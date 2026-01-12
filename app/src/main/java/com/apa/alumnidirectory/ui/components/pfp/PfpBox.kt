package com.apa.alumnidirectory.ui.components.pfp

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apa.alumnidirectory.ui.theme.Primary

@Composable
fun PfpBox(
    id: Int,
    selectedPfp: Int,
    onSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .border(
                width = 4.dp,
                color = if (selectedPfp == id) Primary else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelected(id) },
        contentAlignment = Alignment.Center
    ) {
        when (id) {
            1 -> Pfp1()
            2 -> Pfp2()
            3 -> Pfp3()
            4 -> Pfp4()
            5 -> Pfp5()
            else -> DefaultPfp()
        }
    }
}