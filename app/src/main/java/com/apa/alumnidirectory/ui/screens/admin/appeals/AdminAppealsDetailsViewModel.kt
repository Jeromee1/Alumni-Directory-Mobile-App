package com.apa.alumnidirectory.ui.screens.admin.appeals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.repo.AppealRepo
import com.apa.alumnidirectory.data.repo.UserRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AdminAppealsDetailsViewModel @Inject constructor(
    val appealRepo: AppealRepo,
    val userRepo: UserRepo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val uid: String = savedStateHandle.get<String>("appealId")!!

    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private var _appeal = MutableStateFlow<AppealReq?>(null)
    val appeal = _appeal.asStateFlow()

    init {
        fetchAppeal()
    }

    fun fetchAppeal() {
        viewModelScope.launch {
            safeApiCall {
                appealRepo.fetchAppealById(uid).let { appeal ->
                    _appeal.value = appeal
                }
            }
        }
    }

    fun approveUser() {
        viewModelScope.launch {
            safeApiCall {
                _appeal.value?.let {
                    appealRepo.resolveAppeal(it.uid)
                    userRepo.approveUser(it.userUid).let {
                        _finish.emit(Unit)
                    }
                }
            }
        }
    }

    fun rejectUser(msg: String) {
        viewModelScope.launch {
            safeApiCall {
                _appeal.value?.let {
                    appealRepo.resolveAppeal(it.uid)
                    userRepo.rejectUser(it.userUid, msg).let {
                        _finish.emit(Unit)
                    }
                }
            }
        }
    }
}