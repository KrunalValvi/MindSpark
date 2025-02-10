package com.example.mindspark.presentation.auth.view.security

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreatePinScreen(onPinSet: () -> Unit) {
    var pin by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Create PIN", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = pin, onValueChange = { pin = it }, label = { Text("Enter PIN") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onPinSet() }) {
            Text("Set PIN")
        }
    }
}
