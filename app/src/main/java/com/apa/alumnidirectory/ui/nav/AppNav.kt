package com.apa.alumnidirectory.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apa.alumnidirectory.ui.screens.home.HomeScreen
import com.apa.alumnidirectory.ui.screens.login.LoginScreen
import com.apa.alumnidirectory.ui.screens.register.RegisterScreen

@Composable
fun AppNav(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login,
        modifier = modifier
    ) {
        composable<Screen.Home> { HomeScreen(navController) }
        composable<Screen.Login> { LoginScreen(navController) }
        composable<Screen.Register> { RegisterScreen(navController) }
    }
}