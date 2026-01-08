package com.apa.alumnidirectory.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.ui.FilterOptions
import com.apa.alumnidirectory.data.model.ui.FilterState
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.service.FirebaseAuthService
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
class HomeViewModel @Inject constructor(
    val repo: AuthRepo,
    val firebaseAuth: FirebaseAuthService,
) : BaseViewModel() {
    private var approvedUsers = MutableStateFlow<List<UserData>>(emptyList())

    private var _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState = _filterState.asStateFlow()

    val userList: StateFlow<List<UserData>> = filteredUsers()

    val filterOptions: StateFlow<FilterOptions> = userOptions()

    init {
        fetchUserProfile()
        fetchUsersIfNeeded()
    }

    private fun userOptions(): StateFlow<FilterOptions> {
        return combine(approvedUsers, filterState.map { it.country }) { users, selectedCountry ->
            val countries = users.map { it.location.country }.distinct().sorted()
            val states = users
                .filter { selectedCountry == null || it.location.country == selectedCountry }
                .map { it.location.state }.distinct().sorted()
            val years = users.map { it.graduationYear }.distinct().sorted()
            val stacks = users.map { it.primaryStack }.distinct().sorted()
            FilterOptions(
                countries = countries, states = states, years = years, techStacks = stacks
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FilterOptions()
        )
    }

    private fun filteredUsers(): StateFlow<List<UserData>> {
        return combine(approvedUsers, filterState) { users, filters ->
            UserFilter.filter(users, filters)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            safeApiCall {
                firebaseAuth.getCurrentUser()?.let { user ->
                    _currentUser.update {
                        repo.fetchProfile(user.uid)
                    }
                }
            }
        }
    }

    fun fetchUsersIfNeeded() {
        if (approvedUsers.value.isNotEmpty()) return
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            safeApiCall {
                repo.fetchApprovedUsers().let { users ->
                    approvedUsers.update { users }
                }
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        fetchUsers()
        _isRefreshing.value = false
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

    fun onPrimaryStackSelected(stack: String?) {
        _filterState.update { it.copy(techStack = stack) }
    }

    fun onSortSelected(sort: String) {
        _filterState.update { it.copy(sort = sort) }
    }

    fun clearFilters() {
        _filterState.value = FilterState()
    }
}