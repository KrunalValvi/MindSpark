package com.example.mindspark.presentation.auth.view.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FillProfileScreen(onProfileComplete: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Complete Your Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Bio") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onProfileComplete() }) {
            Text("Continue")
        }
    }
}
