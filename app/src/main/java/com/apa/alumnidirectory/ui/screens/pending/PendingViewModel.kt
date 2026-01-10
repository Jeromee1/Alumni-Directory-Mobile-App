package com.apa.alumnidirectory.ui.screens.pending

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.model.user.CurrentUser
import com.apa.alumnidirectory.data.model.user.FirebaseData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.service.FirebaseAuthService
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PendingViewModel @Inject constructor(
    val repo: AuthRepo,
    val firebaseAuth: FirebaseAuthService
) : BaseViewModel() {
    private var _currentUser = MutableStateFlow<Pair<String, CurrentUser?>>(Pair("", null))
    val currentUser = _currentUser.asStateFlow()

    private var _firebaseUser = MutableStateFlow<FirebaseData?>(null)
    val firebaseUser = _firebaseUser.asStateFlow()

    init {
        updateCurrentUser()
    }

    fun updateCurrentUser() {
        viewModelScope.launch {
            safeApiCall {
                firebaseAuth.getCurrentUser()?.let { user ->
                    val userData = repo.fetchProfile(user.uid)
                    val msg = fetchMessage(userData)
                    _firebaseUser.update { user }
                    _currentUser.update {
                        it.copy(
                            first = msg,
                            second = CurrentUser(
                                firebaseData = user,
                                userData = userData
                            )
                        )
                    }
                }
            }
        }
    }

    private fun fetchMessage(user: UserData): String {
        return when (user.status) {
            Status.PENDING.value -> "Pending Approval"
            Status.REJECTED.value -> "Registration Rejected"
            Status.INACTIVE.value -> "Account Deactivated"
            else -> ""
        }
    }

    fun submitAppeal(text: String) {
        viewModelScope.launch {
            _currentUser.value.second?.userData?.let {
                safeApiCall {
                    repo.submitAppeal(
                        AppealReq(
                            userUid = it.uid,
                            name = it.fullName,
                            email = it.email,
                            msg = text
                        )
                    )
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            safeApiCall {
                firebaseAuth.logout().let {
                    _firebaseUser.value = null
                }
            }
        }
    }
}