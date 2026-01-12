package com.apa.alumnidirectory.ui.screens.admin.pending

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.UserRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AdminPendingViewModel @Inject constructor(
    val repo: UserRepo
) : BaseViewModel() {
    private var _pendingUsers = MutableStateFlow<List<UserData>>(emptyList())
    val pendingUsers = _pendingUsers.asStateFlow()

    private var _cachedUid = MutableStateFlow("")
    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchPendingUsers()
    }

    fun fetchPendingUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            safeApiCall {
                repo.fetchPendingUsers().let { user ->
                    _pendingUsers.update { user }
                }
            }
        }
        _isLoading.value = false
    }

    fun cacheUid(uid: String) {
        _cachedUid.value = uid
    }

    fun approveUser() {
        viewModelScope.launch {
            safeApiCall {
                repo.approveUser(_cachedUid.value).let {
                    fetchPendingUsers()
                }
            }
        }
    }

    fun rejectUser(msg: String) {
        viewModelScope.launch {
            safeApiCall {
                repo.rejectUser(_cachedUid.value, msg).let {
                    fetchPendingUsers()
                }
            }
        }
    }
}