package com.apa.alumnidirectory.ui.screens.admin.appeals

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.ui.components.cards.AdminAppealsUserCard
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun AdminAppealsScreen(
    navController: NavController,
    viewModel: AdminAppealsViewModel = hiltViewModel()
) {
    val appeals by viewModel.appeals.collectAsStateWithLifecycle()
    val unresolvedAppeals = appeals.filter { !it.resolved }
    var showUnresolved by remember { mutableStateOf(false) }

    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullState = rememberPullToRefreshState()

    if(appeals.isNotEmpty()) {
        AdminAppeals(
            if (showUnresolved) unresolvedAppeals else appeals,
            refreshing,
            pullState,
            viewModel::refresh,
            {
                navController.navigate(
                    Screen.AdminAppealsDetails(it)
                )
            },
            showUnresolved
        )
        { showUnresolved = !showUnresolved }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingIcon()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AdminAppeals(
    appeals: List<AppealReq>,
    refreshing: Boolean,
    refreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    navToAppealDetails: (String) -> Unit,
    showUnresolved: Boolean,
    onSwitch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                AnimatedContent(
                    targetState = showUnresolved,
                    transitionSpec = {
                        (slideInHorizontally { it } + fadeIn())
                            .togetherWith(slideOutHorizontally { -it } + fadeOut())
                    }
                ) { unresolved ->
                    Text(
                        if (unresolved) "Pending Appeals" else "All Appeals",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            PullToRefreshBox(
                isRefreshing = refreshing,
                state = refreshState,
                onRefresh = { onRefresh() },
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(appeals) { appeal ->
                        AdminAppealsUserCard(appeal) { navToAppealDetails(appeal.uid) }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { onSwitch() },
            containerColor = Primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(80.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(100)
        ) {
            Icon(
                Icons.Outlined.SwapHoriz, "",
                modifier = Modifier.size(44.dp)
            )
        }
    }
}
