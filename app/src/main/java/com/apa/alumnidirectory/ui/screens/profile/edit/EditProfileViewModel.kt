package com.apa.alumnidirectory.ui.screens.profile.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    val repo: AuthRepo,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {
    val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    val uid = savedStateHandle.get<String>("uid") ?: ""

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            safeApiCall {
                repo.fetchProfile(uid).let {
                    _user.value = it
                }
            }
        }
    }
}