package com.apa.alumnidirectory.ui.screens.pending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun PendingScreen(
    navController: NavController,
    viewModel: PendingViewModel = hiltViewModel()
) {
    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle().value

    LaunchedEffect(currentUser) {
        val status = currentUser.second?.userData?.status
        if (status == Status.APPROVED.value) {
            navController.navigate(Screen.Home) {
                popUpTo(Screen.Pending) { inclusive = true }
            }
        }
    }

    currentUser.second?.let {
        Pending(currentUser.first, it.userData)
    }
}

@Composable
fun Pending(
    statusMsg: String,
    user: UserData
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(0.dp, 120.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.Center
        ) {
            when (user.status) {
                "pending" -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(60.dp),
                        color = Color.White,
                        strokeWidth = 6.dp
                    )
                }

                "rejected" -> {
                    Icon(
                        Icons.Outlined.Cancel,
                        "",
                        modifier = Modifier.size(120.dp)
                    )
                }

                "inactive" -> {
                    Icon(
                        Icons.Default.WarningAmber,
                        "",
                        modifier = Modifier.size(120.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                statusMsg,
                fontSize = 36.sp
            )
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .background(SecondaryG, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        user.fullName,
                        fontSize = 24.sp
                    )
                    Text(
                        user.email,
                        fontSize = 24.sp
                    )
                    user.rejectionMsg?.let {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            it,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    //Logout user and navigate back
                }
            ) {
                Text(
                    "Logout",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Button(
                modifier = Modifier
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    //Pulls up a modal or something
                }
            ) {
                Text(
                    "Contact Admin",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

