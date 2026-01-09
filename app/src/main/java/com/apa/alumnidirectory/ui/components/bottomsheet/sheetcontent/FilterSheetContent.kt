package com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apa.alumnidirectory.data.enums.Filter
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.ui.DropdownData
import com.apa.alumnidirectory.ui.components.inputs.CustomDropdown

@Composable
fun FilterSheetContent(
    onClear: () -> Unit,
    countrySelection: String?,
    sortData: DropdownData,
    techStackData: DropdownData,
    countryData: DropdownData,
    stateData: DropdownData,
    yearData:DropdownData,
    statusData: DropdownData? = null,
    showStatus: Boolean = false
) {
    var selectedFilter by remember { mutableStateOf(Filter.entries.first().value) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Filter",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        CustomDropdown(
            Filter.entries.filter { showStatus || it != Filter.STATUS }.map { it.value },
            selectedFilter,
        ) {
            selectedFilter = it
        }
    }
    //Tech Stack
    if (selectedFilter == Filter.TECH_STACK.value) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Tech Stack",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CustomDropdown(
                    techStackData.list,
                    techStackData.selectedItem
                ) { techStackData.onSelected(it) }
            }
        }
    }
    //Location
    if (selectedFilter == Filter.LOCATION.value) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Country",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CustomDropdown(
                    countryData.list,
                    countryData.selectedItem
                ) { countryData.onSelected(it) }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "State",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CustomDropdown(
                    stateData.list,
                    stateData.selectedItem,
                    countrySelection != null
                ) { stateData.onSelected(it) }
            }
        }
    }
    //Year
    if (selectedFilter == Filter.GRADUATION_YEAR.value) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Year",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CustomDropdown(
                    yearData.list,
                    yearData.selectedItem
                ) { yearData.onSelected(it) }
            }
        }
    }
    //Status
    if (
        selectedFilter == Filter.STATUS.value &&
        statusData != null
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Status",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CustomDropdown(
                    Status.entries.map { status ->
                        status.value.replaceFirstChar { it.uppercase() } },
                    statusData.selectedItem
                ) { statusData.onSelected(it) }
            }
        }
    }
    Spacer(Modifier.height(40.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Sort",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        CustomDropdown(
            sortData.list,
            sortData.selectedItem,
        ) {
            sortData.onSelected(it)
        }
    }
    Spacer(Modifier.height(60.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onClear()
        }
    ) {
        Text(
            "Clear Filters",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
    Spacer(Modifier.height(20.dp))
}