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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(navController: NavController) {
    val context = LocalContext.current
    // For onboarding, we use SharedPreferences.
    val sharedPref = context.getSharedPreferences("onboarding_pref", Context.MODE_PRIVATE)
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

    // Delay to show splash, then decide the next screen.
    // If a user is logged in, check if their profile document exists.
    LaunchedEffect(Unit) {
        delay(1000)
        val user = Firebase.auth.currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Profile exists; user has completed registration.
                        navController.navigate("HomeScreen") {
                            popUpTo("LaunchScreen") { inclusive = true }
                        }
                    } else {
                        // Profile does not exist; user needs to complete registration.
                        navController.navigate("FillProfileScreen") {
                            popUpTo("LaunchScreen") { inclusive = true }
                        }
                    }
                }
                .addOnFailureListener {
                    // On failure, assume profile is incomplete.
                    navController.navigate("FillProfileScreen") {
                        popUpTo("LaunchScreen") { inclusive = true }
                    }
                }
        } else {
            // No logged-in user: if first time, show onboarding; else, show login.
            if (!hasSeenOnboarding) {
                sharedPref.edit().putBoolean("hasSeenOnboarding", true).apply()
                navController.navigate("IntroScreen1") {
                    popUpTo("LaunchScreen") { inclusive = true }
                }
            } else {
                navController.navigate("SignInScreen") {
                    popUpTo("LaunchScreen") { inclusive = true }
                }
            }
        }
    }
}
