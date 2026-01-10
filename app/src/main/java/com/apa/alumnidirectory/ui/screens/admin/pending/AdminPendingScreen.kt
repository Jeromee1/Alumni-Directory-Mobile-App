package com.apa.alumnidirectory.ui.screens.admin.pending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.PendingSheetContent
import com.apa.alumnidirectory.ui.components.cards.AdminPendingUserCard
import com.apa.alumnidirectory.ui.components.confirmation.CustomDialog
import com.apa.alumnidirectory.ui.components.core.EmptyState
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.theme.SecondaryG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPendingScreen(
    navController: NavController,
    viewModel: AdminPendingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val users by viewModel.pendingUsers.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDialog by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    if (!isLoading && users.isNotEmpty()) {
        AdminPending(
            users,
            {
                viewModel.cacheUid(it)
                showDialog = true
            }
        ) {
            scope.launch {
                viewModel.cacheUid(it)
                bottomSheetState.show()
            }
        }

        if (showDialog) {
            CustomDialog(
                { showDialog = false },
                { viewModel.approveUser(); showDialog = false },
                "Approve user?",
                "User would be allowed to gain access to the rest of the app.",
                Icons.Filled.Warning
            )
        }

        CustomBottomSheet(
            bottomSheetState,
            { scope.launch { bottomSheetState.hide() } }
        ) {
            PendingSheetContent(
                "Reason for rejection"
            ) {
                viewModel.rejectUser(it)
                scope.launch { bottomSheetState.hide() }
            }
        }
    } else if (!isLoading) {
        EmptyState("There are no pending users to review")
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingIcon()
        }
    }
}

@Composable
fun AdminPending(
    users: List<UserData>,
    onApproveClick: (String) -> Unit,
    onRejectClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SecondaryG, RoundedCornerShape(12.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "All Pending Users",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(users) { user ->
                AdminPendingUserCard(
                    user,
                    { onApproveClick(user.uid) },
                    {
                        onRejectClick(user.uid)
                    }
                )
            }
        }
    }
}