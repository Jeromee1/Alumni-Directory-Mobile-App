package com.apa.alumnidirectory.ui.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apa.alumnidirectory.ui.components.core.CustomTopBar
import com.apa.alumnidirectory.ui.screens.admin.appeals.AdminAppealsDetailsScreen
import com.apa.alumnidirectory.ui.screens.admin.appeals.AdminAppealsScreen
import com.apa.alumnidirectory.ui.screens.admin.dashboard.DashboardScreen
import com.apa.alumnidirectory.ui.screens.admin.manage.AdminManageScreen
import com.apa.alumnidirectory.ui.screens.admin.pending.AdminPendingScreen
import com.apa.alumnidirectory.ui.screens.home.HomeScreen
import com.apa.alumnidirectory.ui.screens.login.LoginScreen
import com.apa.alumnidirectory.ui.screens.pending.PendingScreen
import com.apa.alumnidirectory.ui.screens.profile.ProfileScreen
import com.apa.alumnidirectory.ui.screens.profile.edit.EditProfileScreen
import com.apa.alumnidirectory.ui.screens.register.RegisterScreen
import com.apa.alumnidirectory.ui.theme.Background
import com.apa.alumnidirectory.ui.theme.Text1

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val dest = navBackStackEntry?.destination

    val showTopBar = when {
        dest == null -> false
        dest.hasRoute<Screen.Login>() ||
        dest.hasRoute<Screen.Register>() ||
        dest.hasRoute<Screen.Pending>() -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = Text1
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(if (!showTopBar) innerPadding else PaddingValues())
        ) {
            if (showTopBar) {
                CustomTopBar(
                    navController = navController,
                    showBackBtn = !dest!!.hasRoute<Screen.Home>()
                )
            }

            Nav(navController)
        }
    }
}

@Composable
fun Nav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login) {
        composable<Screen.Home> { HomeScreen(navController) }
        composable<Screen.Login> { LoginScreen(navController) }
        composable<Screen.Register> { RegisterScreen(navController) }
        composable<Screen.Pending> { PendingScreen(navController) }
        composable<Screen.Dashboard> { DashboardScreen(navController) }
        composable<Screen.AdminPending> { AdminPendingScreen(navController) }
        composable<Screen.AdminManage> { AdminManageScreen(navController) }
        composable<Screen.AdminAppeals> { AdminAppealsScreen(navController) }
        composable<Screen.AdminAppealsDetails> { AdminAppealsDetailsScreen(navController) }
        composable<Screen.Profile> { entry ->
            val adminEntry = entry.savedStateHandle.get<Boolean>("isAdmin") ?: false
            ProfileScreen(isAdmin = adminEntry, navController) }
        composable<Screen.EditProfile> { EditProfileScreen(navController) }
    }
}
