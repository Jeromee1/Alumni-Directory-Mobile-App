package com.apa.alumnidirectory.ui.screens.admin.manage

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.Sort
import com.apa.alumnidirectory.data.model.ui.DropdownData
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.FilterSheetContent
import com.apa.alumnidirectory.ui.components.cards.AdminManageUserCard
import com.apa.alumnidirectory.ui.components.core.EmptyState
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.components.inputs.CustomFilterButton
import com.apa.alumnidirectory.ui.components.inputs.CustomTextField
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.SecondaryG
import com.apa.alumnidirectory.ui.uiutils.FilterPlaceholders
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminManageScreen(
    navController: NavController,
    viewModel: AdminManageViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    val scope = rememberCoroutineScope()

    val users by viewModel.userList.collectAsStateWithLifecycle()
    val filter by viewModel.filterState.collectAsStateWithLifecycle()
    val options by viewModel.filterOptions.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()


    val selectedSort = filter.sort
    val selectedTechStack = filter.techStack ?: FilterPlaceholders.STACK
    val selectedCountry = filter.country ?: FilterPlaceholders.COUNTRY
    val selectedState = filter.state ?: FilterPlaceholders.STATE
    val selectedYear = filter.year ?: FilterPlaceholders.YEAR
    val selectedStatus = filter.status ?: FilterPlaceholders.STATUS

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val pullState = rememberPullToRefreshState()

    if (!isLoading) {
        AdminManage(
            users,
            filter.query,
            isLoading,
            pullState,
            viewModel::refresh,
            { navController.navigate(Screen.Profile(it, isAdmin = true)) },
            { scope.launch { bottomSheetState.show() } },
            viewModel::onSearchChange
        )
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
        FilterSheetContent(
            { viewModel.clearFilters() },
            filter.country,
            DropdownData(
                Sort.entries.map { it.value },
                selectedSort,
                viewModel::onSortSelect
            ),
            DropdownData(
                options.techStacks,
                selectedTechStack,
                viewModel::onPrimaryStackSelect
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
            DropdownData(
                options.status,
                selectedStatus,
                viewModel::onStatusSelect
            ),
            true
        )
    }
}

@Composable
fun AdminManage(
    users: List<UserData>,
    search: String,
    refreshing: Boolean,
    refreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    navToProfile: (String) -> Unit,
    openBottomSheet: () -> Unit,
    onSearchChange: (String) -> Unit
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
                "All Users",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
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
            if (users.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(users) { user ->
                        AdminManageUserCard(user) { navToProfile(user.uid) }
                    }
                }
            } else {
                EmptyState("No users available for this query")
            }
        }
    }
}