package com.example.mindspark.onboarding.model

data class OnboardingScreenContent(
    val title: String,
    val description: String,
    val currentStep: Int,
    val totalSteps: Int = 3,
    val nextRoute: String
)