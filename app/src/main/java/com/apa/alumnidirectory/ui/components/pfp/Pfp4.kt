package com.apa.alumnidirectory.ui.components.pfp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixNormal
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apa.alumnidirectory.ui.theme.Background

@Composable
fun Pfp4() {
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
                .fillMaxSize(0.8f)
                .align(Alignment.Center),
            tint = Color.LightGray
        )
        BoxWithConstraints(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                Icons.Filled.Bolt,
                "",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize(0.1f)
                    .align(Alignment.TopCenter)
                    .offset(
                        x = maxWidth * -0.015f,
                        y = maxHeight * -0.05f
                    )
                    .rotate(5f)
            )
            Icon(
                Icons.Default.AutoFixNormal,
                "",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize(0.4f)
                    .align(Alignment.TopCenter)
                    .offset(
                        x = maxWidth * -0.3f,
                        y = maxHeight * 0.1f
                    )
                    .rotate(-55f)
            )
        }
    }
}