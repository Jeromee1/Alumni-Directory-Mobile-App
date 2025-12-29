package com.apa.alumnidirectory.ui.screens.register

import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val repo: AuthRepo
): BaseViewModel() {
    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    suspend fun register(userReq: RegisterUserReq) {
        // Validate code
        safeApiCall {
            repo.register(userReq).let {
                _finish.emit(Unit)
            }
        }
    }
}