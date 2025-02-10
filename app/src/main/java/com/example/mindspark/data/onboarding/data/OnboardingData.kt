package com.example.mindspark.data.onboarding.data

import com.example.mindspark.data.onboarding.model.OnboardingScreenModel

/**
 * OnboardingData.kt
 *
 * Provides data for onboarding screens.
 */
class OnboardingData {
    private val screens = listOf(
        OnboardingScreenModel("Welcome", "Welcome to MindSpark!", "https://example.com/onboarding1.png"),
        OnboardingScreenModel("Learn", "Access quality courses anywhere.", "https://example.com/onboarding2.png"),
        OnboardingScreenModel("Achieve", "Achieve your learning goals.", "https://example.com/onboarding3.png")
    )

    fun getOnboardingScreens(): List<OnboardingScreenModel> = screens
}
