package com.apa.alumnidirectory.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.service.FirebaseAuthService
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private val location = MutableStateFlow<String?>(null)
    private val year = MutableStateFlow<String?>(null)
    private val techStack = MutableStateFlow<String?>(null)
    val userList: StateFlow<List<UserData>> = filteredUsers()

    init {
        fetchUserProfile()
        fetchUsersIfNeeded()
    }

    private fun filteredUsers(): StateFlow<List<UserData>> {
        return combine(
            approvedUsers,
            _searchQuery,
            location,
            year,
            techStack,
        ) { users, query, location, year, stack ->
            users
                .filter { user ->
                    if (query.isBlank()) true /* true - keep item, false - don't keep */
                    else user.fullName.contains(query, ignoreCase = true)
                }
                .filter { user ->
                    location == null || user.location.country == location
                }
                .filter { user ->
                    year == null || user.graduationYear == year
                }
                .filter { user ->
                    stack == null || user.primaryStack == stack
                }
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
        if(approvedUsers.value.isNotEmpty()) return
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
        _searchQuery.value = query
    }

    fun onLocationSelect(selectedLoc: String?) {
        location.value = selectedLoc
    }

    fun onGradYearSelect(selectedYear: String?) {
        year.value = selectedYear
    }

    fun onPrimaryStackSelected(stack: String?) {
        techStack.value = stack
    }

    fun clearFilters() {
        _searchQuery.value = ""
        location.value = null
        year.value = null
        techStack.value = null
    }
}