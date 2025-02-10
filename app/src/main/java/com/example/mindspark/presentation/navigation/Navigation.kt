package com.example.mindspark.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mindspark.presentation.auth.view.login.LoginScreen
import com.example.mindspark.presentation.auth.view.login.SignInScreen
import com.example.mindspark.presentation.auth.view.register.RegisterScreen
import com.example.mindspark.presentation.auth.view.security.ForgotPasswordScreen
import com.example.mindspark.presentation.home.view.HomeScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignIn : Screen("sign_in")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignIn.route) { SignInScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
    }
}
