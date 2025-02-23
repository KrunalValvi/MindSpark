package com.example.mindspark.onboarding.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.mindspark.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(navController: NavController) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    // Set the status bar color to blue on this splash screen.
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = Color(0xFF0961F5), // Blue for the splash screen.
            darkIcons = false       // Light icons for better contrast.
        )
    }

    // Retrieve shared preferences to determine if onboarding was seen.
    val sharedPref = context.getSharedPreferences("onboarding_pref", Context.MODE_PRIVATE)
    val hasSeenOnboarding = sharedPref.getBoolean("hasSeenOnboarding", false)

    // UI: full-screen blue background with logo.
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

    // After a delay, check user state and navigate accordingly.
    LaunchedEffect(Unit) {
        delay(1000) // Show the splash for 1 second.
        val user = Firebase.auth.currentUser
        if (user != null) {
            // If logged in, check if user profile exists.
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Navigate to HomeScreen and clear the back stack.
                        navController.navigate("HomeScreen") {
                            popUpTo("LaunchScreen") { inclusive = true }
                        }
                    } else {
                        navController.navigate("FillProfileScreen") {
                            popUpTo("LaunchScreen") { inclusive = true }
                        }
                    }
                }
                .addOnFailureListener {
                    navController.navigate("FillProfileScreen") {
                        popUpTo("LaunchScreen") { inclusive = true }
                    }
                }
        } else {
            // If not logged in, navigate to onboarding or sign in.
            if (!hasSeenOnboarding) {
                // Mark onboarding as seen and navigate to IntroScreen1.
                sharedPref.edit().putBoolean("hasSeenOnboarding", true).apply()
                navController.navigate("IntroScreen1") {
                    popUpTo("LaunchScreen") { inclusive = true }
                }
            } else {
                // Navigate to SignInScreen.
                navController.navigate("SignInScreen") {
                    popUpTo("LaunchScreen") { inclusive = true }
                }
            }
        }
    }
}
