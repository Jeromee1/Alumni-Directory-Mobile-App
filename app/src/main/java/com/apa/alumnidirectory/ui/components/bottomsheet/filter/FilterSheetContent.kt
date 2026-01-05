package com.apa.alumnidirectory.ui.components.bottomsheet.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apa.alumnidirectory.ui.components.inputs.CustomDropdown

@Composable
fun FilterSheetContent(
    filterSelected: (String) -> Unit,
    sortSelected: (String) -> Unit,
    countrySelected: (String) -> Unit,
    country: List<String>,
    stateSelected: (String) -> Unit,
    state: List<String>,
    yearsState: (String) -> Unit,
    years: List<String>
) {


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Filter",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        CustomDropdown(

        ) { }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}