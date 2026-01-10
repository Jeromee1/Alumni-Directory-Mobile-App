package com.apa.alumnidirectory.ui.screens.admin.appeals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.PendingSheetContent
import com.apa.alumnidirectory.ui.components.confirmation.CustomDialog
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.theme.Danger
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.SecondaryG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAppealsDetailsScreen(
    navController: NavController,
    viewModel: AdminAppealsDetailsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val appeal = viewModel.appeal.collectAsStateWithLifecycle().value
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.finish.collect {
            navController.popBackStack()
        }
    }
    if(appeal != null) {
        AdminAppealsDetails(
            appeal,
            { navController.navigate(Screen.Profile(it)) },
            { showDialog = true },
            { scope.launch { bottomSheetState.show() } }
        )

        if(showDialog) {
            CustomDialog(
                { showDialog = false },
                { /* Logic here */ },
                "Approve user's appeal?",
                "User would be allowed to gain access to the rest of the app.",
                Icons.Filled.Warning
            )
        }

        CustomBottomSheet(
            bottomSheetState,
            { scope.launch { bottomSheetState.hide() } }
        ) {
            PendingSheetContent(
                "Reason for rejection. Again."
            ) {
                /* Logic here */
            }
        }
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
fun AdminAppealsDetails(
    details: AppealReq,
    navToProfile: (String) -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit
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
                "Appeal Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(SecondaryG, RoundedCornerShape(12.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "${details.name}'s Appeal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                details.email,
                fontSize = 18.sp
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White,
                modifier = Modifier.padding(0.dp, 8.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(details.msg)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Visit profile",
                    modifier = Modifier.clickable { navToProfile(details.userUid) },
                    color = Primary
                )
            }
        }
        if (!details.resolved) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onApprove() },
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        "Approve",
                        modifier = Modifier.padding(0.dp, 6.dp)
                    )
                }
                Button(
                    onClick = { onReject() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Danger
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        "Reject",
                        modifier = Modifier.padding(0.dp, 6.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(4.dp))
    }
}
