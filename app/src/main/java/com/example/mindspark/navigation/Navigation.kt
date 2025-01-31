package com.example.mindspark.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mindspark.auth.ui.login.LoginScreen
import com.example.mindspark.auth.ui.login.SignInScreen
import com.example.mindspark.auth.ui.register.FillProfileScreen
import com.example.mindspark.auth.ui.register.RegisterScreen
import com.example.mindspark.auth.ui.security.CreateNewPassword
import com.example.mindspark.auth.ui.security.CreatePinScreen
import com.example.mindspark.auth.ui.security.ForgotPasswordScreen
import com.example.mindspark.auth.ui.security.SetFingerprintScreen
import com.example.mindspark.auth.ui.security.VerifyForgotPasswordScreen
import com.example.mindspark.communication.ui.InboxScreen
import com.example.mindspark.courses.ui.CoursesListScreen
import com.example.mindspark.courses.ui.PopularCoursesList
import com.example.mindspark.courses.ui.TopMentorScreen
import com.example.mindspark.home.ui.HomeScreen
import com.example.mindspark.home.ui.SearchScreen
import com.example.mindspark.notifications.ui.NotificationsScreen
import com.example.mindspark.onboarding.ui.IntroScreenStep1
import com.example.mindspark.onboarding.ui.IntroScreenStep2
import com.example.mindspark.onboarding.ui.IntroScreenStep3
import com.example.mindspark.onboarding.ui.LaunchScreen
import com.example.mindspark.profile.ui.ProfileScreen
import com.example.mindspark.transactions.ui.TransactionsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    // Define which screens should NOT show the bottom nav bar
    val noBottomBarRoutes = listOf(
        "SignInScreen", "LoginScreen", "RegisterScreen",
        "FillProfileScreen", "CreatePinScreen",
        "SetFingerprintScreen", "ForgotPasswordScreen",
        "VerifyForgotPasswordScreen", "CreateNewPassword",
        "splash", "IntroScreen1", "IntroScreen2", "IntroScreen3"
    )

    Scaffold(
        bottomBar = {
            val currentRoute = currentBackStackEntry.value?.destination?.route
            if (currentRoute !in noBottomBarRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
//            startDestination = "splash",
            startDestination = "SignInScreen",
//            startDestination = "HomeScreen",
            modifier = Modifier.padding(paddingValues)
        ) {

            //onboarding
            composable("splash") { LaunchScreen(navController) }
            composable("IntroScreen1") { IntroScreenStep1(navController) }
            composable("IntroScreen2") { IntroScreenStep2(navController) }
            composable("IntroScreen3") { IntroScreenStep3(navController) }

            //auth
            composable("SignInScreen") { SignInScreen(navController) }
            composable("LoginScreen") { LoginScreen(navController) }
            composable("FillProfileScreen") { FillProfileScreen(navController) }
            composable("RegisterScreen") { RegisterScreen(navController) }
            composable("CreatePinScreen") { CreatePinScreen(navController) }
            composable("SetFingerprintScreen") { SetFingerprintScreen(navController) }
            composable("ForgotPasswordScreen") { ForgotPasswordScreen(navController) }
            composable("VerifyForgotPasswordScreen") { VerifyForgotPasswordScreen(navController) }
            composable("CreateNewPassword") { CreateNewPassword(navController) }

            //user
            composable("HomeScreen") { HomeScreen(navController) }
            composable("CoursesListScreen") { CoursesListScreen(navController) }
            composable("InboxScreen") { InboxScreen(navController) }
            composable("TransactionsScreen") { TransactionsScreen(navController) }


            composable("SearchScreen") { SearchScreen(navController) }
            composable("PopularCoursesList") { PopularCoursesList(navController) }
            composable("CoursesListScreen") { CoursesListScreen(navController) }
            composable("TopMentorScreen") { TopMentorScreen(navController) }
            composable("ProfileScreen") { ProfileScreen(navController) }
            composable("NotificationsScreen") { NotificationsScreen(navController) }
            composable("NotificationsScreen") { NotificationsScreen(navController) }


        }
    }
}
