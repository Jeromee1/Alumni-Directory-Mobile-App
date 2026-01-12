package com.apa.alumnidirectory.ui.screens.admin.appeals

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.repo.AppealRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AdminAppealsViewModel @Inject constructor(
    val repo: AppealRepo
) : BaseViewModel() {
    private var _appeals = MutableStateFlow<List<AppealReq>>(emptyList())
    val appeals = _appeals.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun fetchAppeals() {
        viewModelScope.launch {
            safeApiCall {
                repo.fetchAppeals().let { appl ->
                    _appeals.update { appl.sortedByDescending { it.resolved } }
                }
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        fetchAppeals()
        _isRefreshing.value = false
    }
}
