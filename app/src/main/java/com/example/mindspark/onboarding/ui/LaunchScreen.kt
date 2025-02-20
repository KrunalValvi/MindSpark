package com.example.mindspark.onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import kotlinx.coroutines.delay
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LaunchScreen(navController: NavController) {
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
            // User is logged in, navigate to HomeScreen
            navController.navigate("HomeScreen") {
                popUpTo("LaunchScreen") { inclusive = true }
            }
        } else {
            // No user is logged in, navigate to IntroScreen1 (or LoginScreen as needed)
            navController.navigate("IntroScreen1") {
                popUpTo("LaunchScreen") { inclusive = true }
            }
        }
    }
}
