package com.example.mindspark.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mindspark.auth.ui.login.LoginScreen
import com.example.mindspark.auth.ui.login.SignInScreen
import com.example.mindspark.auth.ui.register.FillProfileScreen
import com.example.mindspark.auth.ui.register.RegisterScreen
import com.example.mindspark.auth.ui.security.*
import com.example.mindspark.communication.ui.InboxScreen
import com.example.mindspark.courses.ui.*
import com.example.mindspark.home.ui.HomeScreen
import com.example.mindspark.home.ui.SearchScreen
import com.example.mindspark.notifications.ui.NotificationsScreen
import com.example.mindspark.onboarding.ui.*
import com.example.mindspark.profile.ui.ProfileScreen
import com.example.mindspark.profile.ui.sections.SecurityScreen
import com.example.mindspark.profile.ui.sections.TermsScreen
import com.example.mindspark.transactions.ui.TransactionsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val noBottomBarRoutes = listOf(
        "SignInScreen",
        "LoginScreen",
        "RegisterScreen",
        "FillProfileScreen",
        "CreatePinScreen",
        "SetFingerprintScreen",
        "ForgotPasswordScreen",
        "VerifyForgotPasswordScreen",
        "CreateNewPassword",
        "splash",
        "IntroScreen1",
        "IntroScreen2",
        "IntroScreen3"
    )

    Scaffold(
        bottomBar = {
            val currentRoute = currentBackStackEntry?.destination?.route
            if (currentRoute !in noBottomBarRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Onboarding
            composable("splash") { LaunchScreen(navController) }
            composable("IntroScreen1") { IntroScreenStep1(navController) }
            composable("IntroScreen2") { IntroScreenStep2(navController) }
            composable("IntroScreen3") { IntroScreenStep3(navController) }

            // Authentication
            composable("SignInScreen") { SignInScreen(navController) }
            composable("LoginScreen") { LoginScreen(navController) }
            composable("FillProfileScreen") { FillProfileScreen(navController) }
            composable("RegisterScreen") { RegisterScreen(navController) }
            composable("CreatePinScreen") { CreatePinScreen(navController) }
            composable("SetFingerprintScreen") { SetFingerprintScreen(navController) }
            composable("ForgotPasswordScreen") { ForgotPasswordScreen(navController) }
            composable("VerifyForgotPasswordScreen") { VerifyForgotPasswordScreen(navController) }
            composable("CreateNewPassword") { CreateNewPassword(navController) }

            // User Screens
            composable(BottomNavItem.Home.route) { HomeScreen(navController) }
            composable(BottomNavItem.MyCourses.route) { CoursesListScreen(navController) }
            composable(BottomNavItem.Inbox.route) { InboxScreen(navController) }
            composable(BottomNavItem.Transactions.route) { TransactionsScreen(navController) }
            composable(BottomNavItem.Profile.route) { ProfileScreen(navController) }
            composable("SearchScreen") { SearchScreen(navController) }
            composable("PopularCoursesList") { PopularCoursesList(navController) }
            composable("TopMentorScreen") { TopMentorScreen(navController) }
            composable("NotificationsScreen") { NotificationsScreen(navController) }
            composable("TermsScreen") { TermsScreen(navController) }
            composable("SecurityScreen") { SecurityScreen(navController) }
            composable("CoursesFilterScreen") { CoursesFilterScreen(navController) }

            composable(
                route = "CourseDetailScreen/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0
                CourseDetailScreen(navController, courseId)
            }

            composable(
                route = "SingleMentorDetails/{mentorId}",
                arguments = listOf(navArgument("mentorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val mentorId = backStackEntry.arguments?.getInt("mentorId") ?: 0
                SingleMentorDetails(navController, mentorId)
            }
        }
    }
}