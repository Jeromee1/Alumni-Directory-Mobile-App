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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.PendingSheetContent
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.SecondaryG
import com.apa.alumnidirectory.ui.uiutils.timeCheckForAppeal
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingScreen(
    navController: NavController,
    viewModel: PendingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle().value
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isLoading by remember { mutableStateOf(true) }
    var submitted by remember { mutableIntStateOf(0) }


    LaunchedEffect(currentUser) {
        val status = currentUser.second?.userData?.status
        if (status == Status.APPROVED.value) {
            navController.navigate(Screen.Home) {
                popUpTo(Screen.Pending) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.firebaseUser.filterNotNull().collect {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.firebaseUser.collect {
            if (it == null && !isLoading) {
                navController.popBackStack()
            }
        }
    }


    currentUser.second?.let {
        if (!isLoading) {
            Pending(
                currentUser.first,
                it.userData,
                submitted,
                viewModel::signOut
            ) { scope.launch { bottomSheetState.show() } }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIcon()
            }
        }

        CustomBottomSheet(
            bottomSheetState,
            { scope.launch { bottomSheetState.hide() } }
        ) {
            PendingSheetContent(
                "Contact Admin",
            ) {
                viewModel.submitAppeal(it)
                scope.launch { bottomSheetState.hide() }
                submitted++
            }
        }
    }
}

@Composable
fun Pending(
    statusMsg: String,
    user: UserData,
    hasSubmitted: Int,
    logout: () -> Unit,
    openBottomSheet: () -> Unit
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
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (timeCheckForAppeal(user.createdAt)) {
                Button(
                    modifier = Modifier
                        .width(200.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { if (hasSubmitted == 0) openBottomSheet() },
                ) {
                    Text(
                        if (hasSubmitted > 0) "Submitted" else "Contact Admin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (hasSubmitted > 0) Color.LightGray else Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Button(
                modifier = Modifier
                    .width(200.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = { logout() }
            ) {
                Text(
                    "Logout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

