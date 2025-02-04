package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.profile.components.NotificationItem
import com.example.mindspark.profile.components.SecurityScreenItem
import com.example.mindspark.ui.theme.LightBlueBackground

@Composable
fun SecurityScreen(navController: NavController) {

    // State for each toggle button
    var RememberMe by remember { mutableStateOf(true) }
    var BiometricID by remember { mutableStateOf(true) }
    var FaceID by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Security",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(horizontal = 18.dp, vertical = 8.dp)
        ) {

            // Notification items
            SecurityScreenItem(
                title = "Remember Me",
                checked = RememberMe,
                onCheckedChange = { RememberMe = it }
            )

            SecurityScreenItem(
                title = "Biometric ID",
                checked = BiometricID,
                onCheckedChange = { BiometricID = it }
            )

            SecurityScreenItem(
                title = "Face ID",
                checked = FaceID,
                onCheckedChange = { FaceID = it }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SecurityScreen(navController = NavController(LocalContext.current))
}