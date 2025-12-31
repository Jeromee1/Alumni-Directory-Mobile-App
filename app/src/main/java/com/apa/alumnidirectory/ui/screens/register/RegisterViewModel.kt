package com.apa.alumnidirectory.ui.screens.register

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.request.RegisterUserReq
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val repo: AuthRepo
) : BaseViewModel() {
    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()


    fun register(userReq: RegisterUserReq) {
        // Validate code
        if (
            !registerValidate(
                validateEmail(userReq.email),
                validatePasswords(userReq.password, userReq.password2)
            )
        ) return
        viewModelScope.launch {
            val success = safeApiCall {
                repo.register(userReq)
            }
            if (success != null) {
                _finish.emit(Unit)
            }
        }
    }
}