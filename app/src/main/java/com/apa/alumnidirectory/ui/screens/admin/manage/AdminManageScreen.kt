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
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.Sort
import com.apa.alumnidirectory.data.enums.SortAdmin
import com.apa.alumnidirectory.data.model.ui.DropdownData
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.FilterSheetContent
import com.apa.alumnidirectory.ui.components.cards.AdminManageUserCard
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.components.inputs.CustomFilterButton
import com.apa.alumnidirectory.ui.components.inputs.CustomTextField
import com.apa.alumnidirectory.ui.theme.SecondaryG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminManageScreen(
    navController: NavController,
//    viewModel: AdminManageViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var search by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(/* user list empty */ false) {
//    AdminManage(
        //    User stuff here,
        //    search,
//        { scope.launch { bottomSheetState.show() } }
    //    ) { search = it }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

        Text("Its set to load forever, do the manage backend stuff and remove this text")

            LoadingIcon()
        }
    }

    CustomBottomSheet(
        bottomSheetState,
        { scope.launch { bottomSheetState.hide() } }
    ) {
        //Add viewModel then uncomment
//        FilterSheetContent(
//            { viewModel.clearFilters() },
//            filter.country,
//            DropdownData(
//                SortAdmin.entries.map { it.value },
//                selectedSort,
//                viewModel::onSortSelected
//            ),
//            DropdownData(
//                options.techStacks,
//                selectedTechStack,
//                viewModel::onPrimaryStackSelected
//            ),
//            DropdownData(
//                options.countries,
//                selectedCountry,
//                viewModel::onCountrySelect
//            ),
//            DropdownData(
//                options.states,
//                selectedState,
//                viewModel::onStateSelect
//            ),
//            DropdownData(
//                options.years,
//                selectedYear,
//                viewModel::onGradYearSelect
//            ),
//        )
    }
}

@Composable
fun AdminManage(
    users: List<UserData>,
    search: String,
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(users) { user ->
                AdminManageUserCard(user) { /* Navigate to profile page */ }
            }
        }
    }
}