package com.apa.alumnidirectory.ui.components.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.Secondary

@Composable
fun CustomTopBar(
    navController: NavController,
    showBackBtn: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Secondary)
            .padding(
                16.dp,
                if(showBackBtn) 30.dp else 8.dp,
                16.dp,
                8.dp
            )
    ) {
        if(showBackBtn) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "",
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { navController.popBackStack() }
                )
                Image(
                    painter = painterResource(R.drawable.alumni_directory_logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navController.navigate(Screen.Home) {
                                popUpTo(Screen.Home) { inclusive = true }
                            }
                        }
                )
            }
        } else {
            Image(
                painter = painterResource(R.drawable.alumni_directory_logo),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
            )
        }
    }
}