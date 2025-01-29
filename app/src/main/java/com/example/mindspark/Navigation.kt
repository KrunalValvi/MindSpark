package com.example.mindspark

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.example.mindspark.courses.ui.CoursesFilterScreen
import com.example.mindspark.courses.ui.CoursesListScreen
import com.example.mindspark.courses.ui.MentorListScreen
import com.example.mindspark.onboarding.ui.IntroScreenStep1
import com.example.mindspark.onboarding.ui.IntroScreenStep2
import com.example.mindspark.onboarding.ui.IntroScreenStep3
import com.example.mindspark.onboarding.ui.LaunchScreen
import com.example.mindspark.home.ui.CategoriesScreen
import com.example.mindspark.home.ui.HomeScreen
import com.example.mindspark.home.ui.NotificationsScreen
import com.example.mindspark.home.ui.PopularCoursesList
import com.example.mindspark.home.ui.SearchScreen
import com.example.mindspark.home.ui.TopMentorScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        //onboarding
        composable("splash") { LaunchScreen(navController) }
        composable("IntroScreen1") { IntroScreenStep1(navController) }
        composable("IntroScreen2") { IntroScreenStep2(navController) }
        composable("IntroScreen3") { IntroScreenStep3(navController) }

        //auth
        composable("LoginScreen") { LoginScreen(navController) }
        composable("SignInScreen") { SignInScreen(navController) }

        composable("FillProfileScreen") { FillProfileScreen(navController) }
        composable("RegisterScreen") { RegisterScreen(navController) }

        composable("CreatePinScreen") { CreatePinScreen(navController) }
        composable("SetFingerprintScreen") { SetFingerprintScreen(navController) }
        composable("ForgotPasswordScreen") { ForgotPasswordScreen(navController) }
        composable("VerifyForgotPasswordScreen") { VerifyForgotPasswordScreen(navController) }
        composable("CreateNewPassword") { CreateNewPassword(navController) }

        //user
        composable("HomeScreen") { HomeScreen(navController) }
        composable("CategoriesScreen") { CategoriesScreen(navController) }
        composable("PopularCoursesList") { PopularCoursesList(navController) }
        composable("TopMentorScreen") { TopMentorScreen(navController) }
        composable("NotificationsScreen") { NotificationsScreen(navController) }
        composable("SearchScreen") { SearchScreen(navController) }
        composable("PopularCoursesList") { PopularCoursesList(navController) }
        composable("CoursesListScreen") { CoursesListScreen(navController) }
        composable("CoursesFilterScreen") { CoursesFilterScreen(navController) }
        composable("MentorListScreen") { MentorListScreen(navController) }

    }
}
