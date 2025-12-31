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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.core.AdminPendingUserCard
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun AdminPendingScreen(
    navController: NavController,
    viewModel: AdminPendingViewModel = hiltViewModel()
) {
    val users by viewModel.pendingUsers.collectAsStateWithLifecycle()
    AdminPending(users, viewModel::approveUser, viewModel::rejectUser)
}

@Composable
fun AdminPending(
    users: List<UserData>,
    onApproveClick: (String) -> Unit,
    onRejectClick: (String, String) -> Unit
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
                "Pending Users",
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
                        //Test function, I need your modal jeremy, here and in pending
                        onRejectClick(user.uid, "Potato")
                    }
                )
            }
        }
    }
}