package com.apa.alumnidirectory.ui.components.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.ui.theme.Secondary
import com.apa.alumnidirectory.ui.theme.SecondaryT

@Composable
fun LoadingIcon() {
    Box(

    ) {
        CircularProgressIndicator(
            strokeWidth = 8.dp,
            trackColor = SecondaryT,
            modifier = Modifier.size(120.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Secondary, RoundedCornerShape(100))
        ) {
            Image(
                painter = painterResource(R.drawable.alumni_directory_logo),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(6.dp)
                    .align(Alignment.Center)
            )
        }
    }
}