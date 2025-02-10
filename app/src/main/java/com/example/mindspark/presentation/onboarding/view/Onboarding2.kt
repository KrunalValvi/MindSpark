package com.example.mindspark.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.mindspark.onboarding.components.OnboardingScreen
import com.example.mindspark.onboarding.data.OnboardingData

@Composable
fun IntroScreenStep2(navController: NavController) {
    OnboardingScreen(
        content = OnboardingData.onboardingScreens[1],
        onSkip = { navController.navigate("SignInScreen") },
        onNext = { navController.navigate("IntroScreen3") }
    )
}