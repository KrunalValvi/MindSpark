package com.example.mindspark.onboarding.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import kotlinx.coroutines.delay
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LaunchScreen(navController: NavController) {
    val context = LocalContext.current
    // Get SharedPreferences; you could use "onboarding_pref" as the file name.
    val sharedPref = context.getSharedPreferences("onboarding_pref", Context.MODE_PRIVATE)
    // Check if the user has seen the onboarding screens.
    val hasSeenOnboarding = sharedPref.getBoolean("hasSeenOnboarding", false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0961F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_launchscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }

    LaunchedEffect(Unit) {
        delay(1000)
        val user = Firebase.auth.currentUser
        if (user != null) {
            // If the user is logged in, navigate to HomeScreen.
            navController.navigate("HomeScreen") {
                popUpTo("LaunchScreen") { inclusive = true }
            }
        } else {
            if (!hasSeenOnboarding) {
                // First time user: navigate to the onboarding screens.
                // Set the flag so next time onboarding is skipped.
                sharedPref.edit().putBoolean("hasSeenOnboarding", true).apply()
                navController.navigate("IntroScreen1") {
                    popUpTo("LaunchScreen") { inclusive = true }
                }
            } else {
                // On subsequent launches, go straight to the LoginScreen.
                navController.navigate("LoginScreen") {
                    popUpTo("LaunchScreen") { inclusive = true }
                }
            }
        }
    }
}
