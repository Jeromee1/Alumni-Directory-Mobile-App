package com.apa.alumnidirectory.ui.components.pfp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apa.alumnidirectory.ui.theme.Background

@Composable
fun DefaultPfp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .background(Background, RoundedCornerShape(12.dp))
    ) {
        Icon(
            Icons.Filled.Person,
            "",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            tint = Color.LightGray
        )
    }
}