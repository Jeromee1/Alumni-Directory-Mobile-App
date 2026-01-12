package com.apa.alumnidirectory.ui.screens.admin.manage

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.ui.FilterOptions
import com.apa.alumnidirectory.data.model.ui.FilterState
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.UserRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import com.apa.alumnidirectory.ui.uiutils.UserFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AdminManageViewModel @Inject constructor(
    val repo: UserRepo
) : BaseViewModel() {
    private var allUsers = MutableStateFlow<List<UserData>>(emptyList())
    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState = _filterState.asStateFlow()

    val userList: StateFlow<List<UserData>> = filteredUsers()

    val filterOptions: StateFlow<FilterOptions> = adminUserOptions()

    init {
        fetchUsersIfNeeded()
    }

    private fun adminUserOptions(): StateFlow<FilterOptions> {
        return combine(allUsers, filterState.map { it.country })
        { users, selectedCountry ->
            val countries = users.map { it.location.country }.distinct().sorted()
            val states = users
                .filter { selectedCountry == null || it.location.country == selectedCountry }
                .map { it.location.state }.distinct().sorted()
            val years = users.map { it.graduationYear }.distinct().sorted()
            val stacks = users.map { it.primaryStack }.distinct().sorted()
            FilterOptions(
                countries = countries,
                states = states,
                years = years,
                techStacks = stacks,
                status = Status.entries.map { it.value }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FilterOptions()
        )
    }

    private fun filteredUsers(): StateFlow<List<UserData>> {
        return combine(allUsers, filterState) { users, filters ->
            UserFilter.filter(users, filters)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    }

    fun fetchUsersIfNeeded() {
        if (allUsers.value.isNotEmpty()) return
        fetchUsers()
    }

    fun fetchUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            safeApiCall {
                repo.fetchAllUsers().let { users ->
                    allUsers.update { users }
                }
            }
        }
        _isLoading.value = false
    }

    fun refresh() {
        _isLoading.value = true
        fetchUsers()
        _isLoading.value = false
    }

    fun onSearchChange(query: String) {
        _filterState.update { it.copy(query = query) }
    }

    fun onCountrySelect(selectedCountry: String?) {
        _filterState.update { it.copy(country = selectedCountry, state = null) }
    }

    fun onStateSelect(selectedState: String?) {
        _filterState.update { it.copy(state = selectedState) }
    }

    fun onGradYearSelect(selectedYear: String?) {
        _filterState.update { it.copy(year = selectedYear) }
    }

    fun onPrimaryStackSelect(stack: String?) {
        _filterState.update { it.copy(techStack = stack) }
    }

    fun onSortSelect(sort: String) {
        _filterState.update { it.copy(sort = sort) }
    }

    fun onStatusSelect(status: String) {
        _filterState.update { it.copy(status = status) }
    }

    fun clearFilters() {
        _filterState.value = FilterState()
    }
}