package com.example.mindspark.onboarding.data

import com.example.mindspark.onboarding.model.OnboardingScreenContent

object OnboardingData {
    val onboardingScreens = listOf(
        OnboardingScreenContent(
            title = "Online Learning",
            description = "We Provide Classes Online Classes and Pre Recorded\nLeactures.!",
            currentStep = 1,
            nextRoute = "IntroScreen2"
        ),
        OnboardingScreenContent(
            title = "Learn from Anytime",
            description = "Booked or Same the Lectures for Future",
            currentStep = 2,
            nextRoute = "IntroScreen3"
        ),
        OnboardingScreenContent(
            title = "Get Online Certificate",
            description = "Analyse your scores and Track your results",
            currentStep = 3,
            nextRoute = "SignInScreen"
        )
    )
}