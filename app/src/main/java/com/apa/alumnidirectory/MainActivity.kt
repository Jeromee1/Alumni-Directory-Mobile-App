package com.apa.alumnidirectory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apa.alumnidirectory.ui.nav.AppNav
import com.apa.alumnidirectory.ui.theme.AlumniDirectoryTheme
import com.apa.alumnidirectory.ui.theme.Background
import com.apa.alumnidirectory.ui.theme.Text1
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlumniDirectoryTheme {
                ComposeApp()
            }
        }
    }
}

@Composable
fun ComposeApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = Text1
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Background)
        ) {
            Box(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
            ) {
                AppNav()
            }
        }
    }
}