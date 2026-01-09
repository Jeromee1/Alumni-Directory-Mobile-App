package com.apa.alumnidirectory.ui.screens.admin.appeals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.repo.AuthRepo
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
    val repo: AuthRepo,
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
                repo.fetchAppealById(uid).let { appeal ->
                    _appeal.value = appeal
                }
            }
        }
    }

    fun approveUser() {
        viewModelScope.launch {
            safeApiCall {
                _appeal.value?.let {
                    repo.resolveAppeal(it.uid)
                    repo.approveUser(it.userUid).let {
                        _finish.emit(Unit)
                    }
                }
            }
        }
    }

    fun rejectUser() {
        viewModelScope.launch {
            safeApiCall {
                _appeal.value?.let {
                    repo.resolveAppeal(it.uid)
                    repo.rejectUser(it.userUid, "").let {
                        _finish.emit(Unit)
                    }
                }
            }
        }
    }
}