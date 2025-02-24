package com.example.mindspark.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mindspark.auth.ui.login.LoginScreen
import com.example.mindspark.auth.ui.login.SignInScreen
import com.example.mindspark.auth.ui.register.RegisterScreen
import com.example.mindspark.auth.ui.register.FillProfileScreen
import com.example.mindspark.auth.ui.security.CreateNewPassword
import com.example.mindspark.auth.ui.security.CreatePinScreen
import com.example.mindspark.auth.ui.security.ForgotPasswordScreen
import com.example.mindspark.auth.ui.security.SetFingerprintScreen
import com.example.mindspark.auth.ui.security.VerifyForgotPasswordScreen
import com.example.mindspark.onboarding.ui.IntroScreenStep1
import com.example.mindspark.onboarding.ui.IntroScreenStep2
import com.example.mindspark.onboarding.ui.IntroScreenStep3
import com.example.mindspark.onboarding.ui.LaunchScreen

fun NavGraphBuilder.AuthenticationNavigation(navController: NavController) {
    composable(
        "LaunchScreen",
//        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
//        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { LaunchScreen(navController) }

        composable(
        "IntroScreenStep1",
//        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
//        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { IntroScreenStep1(navController) }

        composable(
        "IntroScreenStep2",
//        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
//        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { IntroScreenStep2(navController) }

        composable(
        "IntroScreenStep3",
//        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
//        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { IntroScreenStep3(navController) }

        composable(
        "SignInScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { SignInScreen(navController) }

    composable(
        "LoginScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { LoginScreen(navController) }

    composable(
        "RegisterScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(700)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(700)) }
    ) { RegisterScreen(navController) }

    composable(
        "FillProfileScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { FillProfileScreen(navController) }

    composable(
        "ForgotPasswordScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { ForgotPasswordScreen(navController) }

    composable(
        "CreatePinScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { CreatePinScreen(navController) }

    composable(
        "SetFingerprintScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { SetFingerprintScreen(navController) }

    composable(
        "VerifyForgotPasswordScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { VerifyForgotPasswordScreen(navController) }

    composable(
        "CreateNewPassword",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { CreateNewPassword(navController) }

}
