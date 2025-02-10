package com.example.mindspark.presentation.auth.view.security

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerifyForgotPasswordScreen(onVerificationSuccess: () -> Unit) {
    var verificationCode by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Verify Forgot Password", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = verificationCode, onValueChange = { verificationCode = it }, label = { Text("Enter Verification Code") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onVerificationSuccess() }) {
            Text("Verify")
        }
    }
}
