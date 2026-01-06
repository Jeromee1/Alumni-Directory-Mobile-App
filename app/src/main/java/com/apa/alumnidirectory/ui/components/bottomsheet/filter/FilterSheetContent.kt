package com.apa.alumnidirectory.ui.components.bottomsheet.filter

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
import com.apa.alumnidirectory.data.enums.Sort
import com.apa.alumnidirectory.ui.components.inputs.CustomDropdown

@Composable
fun FilterSheetContent(
    filterOnSelected: (String) -> Unit,
    sortOnSelected: (String) -> Unit,
    techStackOnSelected: (String) -> Unit,
    techStack: List<String>,
    selectedTechStack: String,
    countryOnSelected: (String) -> Unit,
    country: List<String>,
    selectedCountry: String,
    stateOnSelected: (String) -> Unit,
    state: List<String>,
    selectedState: String,
    yearsOnSelected: (String) -> Unit,
    years: List<String>,
    selectedYear: String
) {
    var selectedFilter by remember { mutableStateOf(Filter.entries.first().value) }
    var selectedSort by remember { mutableStateOf(Sort.entries.first().value) }

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
            Filter.entries.map { it.value },
            selectedFilter,
        ) {
            filterOnSelected(it)
            selectedFilter = it
        }
    }
    //Tech Stack
    if(selectedFilter == Filter.TECH_STACK.value) {
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
                    techStack,
                    selectedTechStack
                ) { techStackOnSelected(it) }
            }
        }
    }
    //Location
    if(selectedFilter == Filter.LOCATION.value) {
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
                    country,
                    selectedCountry
                ) { countryOnSelected(it) }
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
                    state,
                    selectedState
                ) { stateOnSelected(it) }
            }
        }
    }
    //Year
    if(selectedFilter == Filter.GRADUATION_YEAR.value) {
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
                    years,
                    selectedYear
                ) { yearsOnSelected(it) }
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
            Sort.entries.map { it.value },
            selectedSort,
        ) {
            sortOnSelected(it)
            selectedSort = it
        }
    }
    Spacer(Modifier.height(60.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = { /* Apply filter & sort */ }
    ) {
        Text(
            "Apply Changes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
    Spacer(Modifier.height(20.dp))
}