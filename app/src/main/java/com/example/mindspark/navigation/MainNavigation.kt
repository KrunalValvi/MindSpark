package com.example.mindspark.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mindspark.onboarding.ui.LaunchScreen
import com.example.mindspark.navigation.components.BottomNavigationBar
import com.example.mindspark.onboarding.ui.IntroScreenStep1
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppNavigation() {
    // Create a NavController to manage navigation.
    val navController = rememberNavController()
    // Observe the current back stack entry.
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    // System UI controller to update status bar colors.
    val systemUiController = rememberSystemUiController()

    // List of routes where the bottom bar should be hidden.
    val noBottomBarRoutes = listOf(
        "SignInScreen",
        "LoginScreen",
        "RegisterScreen",
        "FillProfileScreen",
        "CreatePinScreen",
        "SetFingerprintScreen",
        "ForgotPasswordScreen",
        "LaunchScreen", // our splash screen
        "IntroScreen1",
        "IntroScreen2",
        "IntroScreen3",
        "ActiveCallScreen"
    )

    // Reset the status bar color to a default for all screens except the LaunchScreen.
    LaunchedEffect(currentBackStackEntry?.destination?.route) {
        val currentRoute = currentBackStackEntry?.destination?.route
        if (currentRoute != "LaunchScreen") {
            systemUiController.setStatusBarColor(
                color = Color(0xFFF5F9FF), // Default app background color.
                darkIcons = true         // Dark icons on a light background.
            )
        }
    }

    Scaffold(
        bottomBar = {
            // Show the bottom bar only for routes not in noBottomBarRoutes.
            val currentRoute = currentBackStackEntry?.destination?.route
            if (currentRoute !in noBottomBarRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        // Define the navigation graph.
        NavHost(
            navController = navController,
            startDestination = "LaunchScreen", // Start with the splash screen.
            modifier = Modifier.padding(paddingValues)
        ) {
            // Splash/Launch screen destination.
            composable("LaunchScreen") { LaunchScreen(navController) }
            // Onboarding screen destination.
            composable("IntroScreenStep1") { IntroScreenStep1(navController) }
            // Add your other navigation graphs (Authentication and User navigation).
            AuthenticationNavigation(navController)
            UserNavigation(navController)
            // (If you have IntroScreen2/3, add them similarly.)
        }
    }
}
