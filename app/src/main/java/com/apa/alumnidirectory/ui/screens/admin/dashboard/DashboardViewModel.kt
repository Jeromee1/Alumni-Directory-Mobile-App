package com.apa.alumnidirectory.ui.screens.admin.dashboard

import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.ui.DashboardUiState
import com.apa.alumnidirectory.data.repo.AppealRepo
import com.apa.alumnidirectory.data.repo.UserRepo
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val userRepo: UserRepo,
    val appealRepo: AppealRepo
) : BaseViewModel() {
    private var _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            safeApiCall {
                val appeals = appealRepo.fetchUnresolvedAppeal()
                val pendingUsers = userRepo.fetchPendingUsers()
                val approvedUsers = userRepo.fetchApprovedUsers()

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