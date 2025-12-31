package com.apa.alumnidirectory.ui.screens.admin.pending

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
class AdminPendingViewModel @Inject constructor(
    val repo: AuthRepo
) : BaseViewModel() {
    private var _pendingUsers = MutableStateFlow<List<UserData>>(emptyList())
    val pendingUsers = _pendingUsers.asStateFlow()

    init {
        fetchPendingUsers()
    }

    fun fetchPendingUsers() {
        viewModelScope.launch {
            safeApiCall {
                repo.fetchPendingUsers().let { user ->
                    _pendingUsers.update { user }
                }
            }
        }
    }

    fun approveUser(uid: String) {
        viewModelScope.launch {
            safeApiCall {
                repo.approveUser(uid)
            }
        }
    }

    fun rejectUser(uid: String, msg: String) {
        viewModelScope.launch {
            safeApiCall {
                //Test function
                repo.rejectUser(uid, msg)
            }
        }
    }
}