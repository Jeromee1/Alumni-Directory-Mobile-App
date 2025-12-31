package com.apa.alumnidirectory.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.core.HomeUserCard
import com.apa.alumnidirectory.ui.components.inputs.CustomFilterButton
import com.apa.alumnidirectory.ui.components.inputs.CustomTextField
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.Primary
import kotlin.String

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val users by viewModel.userList.collectAsStateWithLifecycle()

    var search by remember { mutableStateOf("") }

    //Refreshing code
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullState = rememberPullToRefreshState()

    Home(
        users,
        search,
        refreshing,
        pullState,
        viewModel::refresh,
        { navController.navigate(Screen.Dashboard) },
        {/*navController.navigate(Screen.Profile)*/ }
    )
    { search = it }
}

@Composable
fun Home(
    users: List<UserData>,
    search: String,
    refreshing: Boolean,
    refreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    navToDashboard: () -> Unit,
    navToProfile: (String) -> Unit,
    onSearchChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier.weight(0.7f)
                ) {
                    CustomTextField(
                        FieldData("Search", search, onSearchChange)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight()
                ) {
                    CustomFilterButton { }
                }
            }
            PullToRefreshBox(
                isRefreshing = refreshing,
                state = refreshState,
                onRefresh = { onRefresh() },
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(users) { user ->
                        HomeUserCard(user) { navToProfile(user.uid) }
                    }
                }
            }
        }
        if(/* Is User an Admin check */ true) {
            FloatingActionButton(
                onClick = { navToDashboard() },
                containerColor = Primary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(80.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(100)
            ) {
                Icon(
                    Icons.Outlined.AdminPanelSettings, "",
                    modifier = Modifier.size(44.dp)
                )
            }
        }
        FloatingActionButton(
            onClick = { /* Nav to profile(self) */ },
            containerColor = Primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(80.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(100)
        ) {
            Icon(
                Icons.Outlined.Person, "",
                modifier = Modifier.size(44.dp)
            )
        }
    }
}