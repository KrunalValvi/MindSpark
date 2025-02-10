package com.example.mindspark.presentation.auth.view.security

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FingerprintScreen(onAuthenticated: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Fingerprint Authentication", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        // Original design: A button to simulate fingerprint authentication.
        Button(onClick = { onAuthenticated() }) {
            Text("Authenticate with Fingerprint")
        }
    }
}
