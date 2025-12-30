package com.apa.alumnidirectory.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: AuthRepo
) : BaseViewModel() {
    private var _userList = MutableStateFlow<List<UserData>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            safeApiCall {
                repo.fetchApprovedUsers().let { users ->
                    _userList.update { users }
                }
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        fetchUsers()
        _isRefreshing.value = false
    }
}