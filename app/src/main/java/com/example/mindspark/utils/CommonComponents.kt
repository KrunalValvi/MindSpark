package com.example.mindspark.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NoInternetWarning(isConnected: Boolean) {
    if (!isConnected) {
        Surface(
            modifier = Modifier.fillMaxWidth().background(Color.Red).padding(8.dp),
            color = Color.Red
        ) {
            Text(
                text = "No Internet Connection!",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
