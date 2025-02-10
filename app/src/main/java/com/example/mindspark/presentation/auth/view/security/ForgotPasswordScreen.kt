package com.example.mindspark.presentation.auth.view.security

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(onResetRequested: () -> Unit) {
    var email by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Forgot Password", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Enter Email") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onResetRequested() }) {
            Text("Reset Password")
        }
    }
}
