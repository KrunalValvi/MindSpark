package com.example.mindspark.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mindspark.onboarding.components.OnboardingScreen
import com.example.mindspark.onboarding.data.OnboardingData

@Composable
fun IntroScreenStep1(navController: NavController) {
    OnboardingScreen(
        content = OnboardingData.onboardingScreens[0],
        onSkip = { navController.navigate("SignInScreen") },
        onNext = { navController.navigate("IntroScreen2") }
    )
}

@Preview(showBackground = true)
@Composable
fun IntroScreenStep1Preview() {
    IntroScreenStep1(navController = NavController(LocalContext.current))
}