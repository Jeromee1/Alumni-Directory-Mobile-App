package com.apa.alumnidirectory.ui.screens.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.auth.LoginReq
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repo: AuthRepo
): BaseViewModel() {

    fun login(loginReq: LoginReq) {
        viewModelScope.launch {
            safeApiCall { repo.login(loginReq).let {
                Log.d("debug", it.toString())
            } }
        }
    }
}