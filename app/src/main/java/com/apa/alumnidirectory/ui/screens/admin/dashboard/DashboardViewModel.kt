package com.apa.alumnidirectory.ui.screens.admin.dashboard

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.ui.DashboardUiState
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val repo: AuthRepo
) : BaseViewModel() {
    private var _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            safeApiCall {
                val appeals = repo.fetchUnresolvedAppeal()
                val pendingUsers = repo.fetchPendingUsers()
                val approvedUsers = repo.fetchApprovedUsers()

                val oneWeekAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)

                val recentApproved = approvedUsers.filter {
                    it.status == Status.APPROVED.value
                            && it.approvedAt != null
                            && it.approvedAt >= oneWeekAgo
                }
                _uiState.update {
                    it.copy(
                        appealCount = appeals.size,
                        pendingCount = pendingUsers.size,
                        approvedCount = approvedUsers.size,
                        recentApprovedCount = recentApproved.size
                    )
                }
            }
        }
    }
}