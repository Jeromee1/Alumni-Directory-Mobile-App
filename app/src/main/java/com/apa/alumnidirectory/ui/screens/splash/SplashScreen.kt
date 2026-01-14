package com.apa.alumnidirectory.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.ui.nav.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    var state by remember { mutableIntStateOf(0) }
    var bg by remember { mutableStateOf(true) }

    val animationDuration = tween<Float>(durationMillis = 1000)

    val logoScale by animateFloatAsState(
        targetValue = when(state) { 1 -> 0.7f; 2 -> 0.55f; else -> 0.3f },
        animationSpec = animationDuration
    )
    val logoRotation by animateFloatAsState(
        targetValue = if (state == 1) -12f else 0f,
        animationSpec = animationDuration
    )
    val logoOffsetXMultiplier by animateFloatAsState(
        targetValue = when(state) { 1 -> -0.07f; 2 -> 0f; else -> -0.01f },
        animationSpec = animationDuration
    )
    val logoOffsetYMultiplier by animateFloatAsState(
        targetValue = when(state) { 1 -> -0.28f; 2 -> 0f; else -> -0.1f },
        animationSpec = animationDuration
    )
    val logoPadding by animateDpAsState(
        targetValue = if (state == 1) 50.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000)
    )
    val personScale by animateFloatAsState(
        targetValue = when(state) { 1 -> 0.3f; 2 -> 0.2f; else -> 0.5f } ,
        animationSpec = animationDuration
    )
    val personOffsetYMultiplier by animateFloatAsState(
        targetValue = when(state) { 0 -> 0f; else -> 0.2f },
        animationSpec = animationDuration
    )
    val personAlpha by animateFloatAsState(
        targetValue = when(state) { 1 -> 0.2f; 2 -> 0f; else -> 1f },
        animationSpec = animationDuration
    )
    val backgroundAlpha by animateFloatAsState(
        targetValue = if(bg) {
            when(state) { 1 -> 0.2f; 2 -> 0.3f; else -> 0f }
        } else 0f,
        animationSpec = animationDuration
    )

    LaunchedEffect(Unit) {
        state = 0
        delay(800)
        state = 1
        delay(1500)
        state = 2
        delay(2000)
        bg = false
        delay(1000)
        navController.navigate(Screen.Login) {
            popUpTo(Screen.Splash) { inclusive = true }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0f, 0f, 0f, backgroundAlpha))
    ) {
        Icon(
            Icons.Filled.Person,
            "",
            modifier = Modifier
                .fillMaxSize(personScale)
                .align(Alignment.Center)
                .offset(
                    y = (maxHeight.value * personOffsetYMultiplier).dp
                )
                .graphicsLayer { alpha = personAlpha },
            tint = Color.LightGray
        )
        Image(
            painter = painterResource(R.drawable.alumni_directory_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(logoScale)
                .padding(top = logoPadding)
                .align(Alignment.Center)
                .offset(
                    x = (maxWidth.value * logoOffsetXMultiplier).dp,
                    y = (maxHeight.value * logoOffsetYMultiplier).dp
                )
                .graphicsLayer { rotationZ = logoRotation }
        )
    }
}