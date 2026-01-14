package com.apa.alumnidirectory.ui.components.pfp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.ui.theme.Background

@Composable
fun Pfp1() {
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
                .fillMaxSize(0.9f)
                .align(Alignment.BottomCenter),
            tint = Color.LightGray
        )
        BoxWithConstraints(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.alumni_directory_logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.55f)
                    .align(Alignment.TopCenter)
                    .offset(
                        x = maxWidth * -0.07f,
                        y = maxHeight * -0.28f
                    )
                    .rotate(-12f)
            )
        }
    }
}