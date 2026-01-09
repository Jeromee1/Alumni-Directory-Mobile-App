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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Sort
import com.apa.alumnidirectory.data.model.ui.DropdownData
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.FilterSheetContent
import com.apa.alumnidirectory.ui.components.cards.HomeUserCard
import com.apa.alumnidirectory.ui.components.inputs.CustomFilterButton
import com.apa.alumnidirectory.ui.components.inputs.CustomTextField
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.uiutils.FilterPlaceholders
import kotlinx.coroutines.launch
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val users by viewModel.userList.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val filter by viewModel.filterState.collectAsStateWithLifecycle()
    val options by viewModel.filterOptions.collectAsStateWithLifecycle()

    //Refreshing code
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullState = rememberPullToRefreshState()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Home(
        user,
        users,
        filter.query,
        refreshing,
        pullState,
        viewModel::refresh,
        { navController.navigate(Screen.Dashboard) },
        { navController.navigate(Screen.Profile(it)) },
        { scope.launch { bottomSheetState.show() } },
        viewModel::onSearchChange
    )

    val selectedSort = filter.sort
    val selectedTechStack = filter.techStack ?: FilterPlaceholders.STACK
    val selectedCountry = filter.country ?: FilterPlaceholders.COUNTRY
    val selectedState = filter.state ?: FilterPlaceholders.STATE
    val selectedYear = filter.year ?: FilterPlaceholders.YEAR

    CustomBottomSheet(
        bottomSheetState,
        { scope.launch { bottomSheetState.hide() } }
    ) {
        FilterSheetContent(
            { viewModel.clearFilters() },
            filter.country,
            DropdownData(
                Sort.entries.map { it.value },
                selectedSort,
                viewModel::onSortSelected
            ),
            DropdownData(
                options.techStacks,
                selectedTechStack,
                viewModel::onPrimaryStackSelected
            ),
            DropdownData(
                options.countries,
                selectedCountry,
                viewModel::onCountrySelect
            ),
            DropdownData(
                options.states,
                selectedState,
                viewModel::onStateSelect
            ),
            DropdownData(
                options.years,
                selectedYear,
                viewModel::onGradYearSelect
            ),
        )
    }
}


@Composable
fun Home(
    user: UserData?,
    users: List<UserData>,
    search: String,
    refreshing: Boolean,
    refreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    navToDashboard: () -> Unit,
    navToProfile: (String) -> Unit,
    openBottomSheet: () -> Unit,
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
                        FieldData("Search", search, onValueChange = onSearchChange)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight()
                ) {
                    CustomFilterButton { openBottomSheet() }
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
        user?.let {
            if (user.role == Roles.ADMIN.value) {
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
                onClick = { navToProfile(it.uid) },
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
}