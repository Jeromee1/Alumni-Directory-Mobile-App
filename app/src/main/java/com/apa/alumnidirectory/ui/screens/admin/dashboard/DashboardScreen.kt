package com.apa.alumnidirectory.ui.screens.admin.dashboard

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.ui.DashboardUiState
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadDashboard()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashBoard(
        uiState,
        { navController.navigate(Screen.AdminPending) },
        { navController.navigate(Screen.AdminAppeals) },
        { navController.navigate(Screen.AdminManage) }
    )
}

@Composable
fun DashBoard(
    uiState: DashboardUiState,
    navToPending: () -> Unit,
    navToAppeals: () -> Unit,
    navToManage: () -> Unit
) {
    val approvedCount by animateIntAsState(
        targetValue = uiState.approvedCount,
        animationSpec = tween(800),
    )

    val recentApprovedCount by animateIntAsState(
        targetValue = uiState.recentApprovedCount,
        animationSpec = tween(800),
    )

    val pendingCount by animateIntAsState(
        targetValue = uiState.pendingCount,
        animationSpec = tween(800),
    )

    val appealCount by animateIntAsState(
        targetValue = uiState.appealCount,
        animationSpec = tween(800),
    )

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
                "Admin Dashboard",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(SecondaryG, RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("$approvedCount",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold)
                        Text("Total", fontWeight = FontWeight.Bold)
                    }
                    VerticalDivider(
                        color = Color(255,255,255,80),
                        thickness = 1.dp
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("$recentApprovedCount",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold)
                        Text("Recent", fontWeight = FontWeight.Bold)
                    }
                }
                HorizontalDivider(
                    color = Color(255,255,255,80),
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("$pendingCount",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold)
                        Text("Pending", fontWeight = FontWeight.Bold)
                    }
                    VerticalDivider(
                        color = Color(255,255,255,80),
                        thickness = 1.dp
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("$appealCount",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold)
                        Text("Appeals", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Button(
                    onClick = { navToPending() },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Pending Users",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = { navToAppeals() },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Appeals",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                onClick = { navToManage() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Manage Users",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}