package com.apa.alumnidirectory.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.model.user.CurrentUser
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
class ProfileViewModel @Inject constructor(
    val repo: AuthRepo,
    val firebaseAuth: FirebaseAuthService,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private var _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser = _currentUser.asStateFlow()
    private val userUid = savedStateHandle.get<String>("userUid")!!
    private var _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    init {
        fetchLoggedInUser()
    }

    fun fetchLoggedInUser() {
        viewModelScope.launch {
            safeApiCall {
                firebaseAuth.getCurrentUser()?.let { user ->
                    val userData = repo.fetchProfile(user.uid)
                    _currentUser.update {
                        CurrentUser(
                            firebaseData = user,
                            userData = userData
                        )
                    }
                }
            }
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            safeApiCall {

                repo.fetchProfile(userUid).let { userData ->
                    _user.update { userData }
                }
            }
        }
    }

    fun permissionCheck(): Boolean {
        val user = _user.value
        val currentUser = currentUser.value
        return (user?.uid == currentUser?.firebaseData?.uid
                || currentUser?.userData?.role == Roles.ADMIN.value)
    }
}