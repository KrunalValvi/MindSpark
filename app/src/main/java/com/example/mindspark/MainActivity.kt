package com.example.mindspark

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mindspark.navigation.AppNavigation
import com.example.mindspark.ui.theme.MindSparkTheme
import com.example.mindspark.utils.NoInternetWarning
import com.example.mindspark.utils.rememberNetworkState
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Handle permission denied case if needed
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted
                }

                shouldShowRequestPermissionRationale(
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    // Show in-app rationale and request permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }

                else -> {
                    // Directly ask for the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check notification permission when app starts
        checkNotificationPermission()
        checkPlayServices()

        setContent {
            MindSparkTheme {
                var showPermissionDialog by remember { mutableStateOf(false) }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    showPermissionDialog = true
                }

                val isConnected = rememberNetworkState(this)
                val navigateToChat = intent.getBooleanExtra("navigate_to_chat", false)
                val chatTitle = intent.getStringExtra("chat_title") ?: ""
                val senderEmail = intent.getStringExtra("sender_email") ?: ""

                if (showPermissionDialog) {
                    AlertDialog(
                        onDismissRequest = { showPermissionDialog = false },
                        title = { Text("Enable Notifications") },
                        text = {
                            Text(
                                "To receive chat messages and important updates, please enable notifications for MindSpark.",
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        },
                        confirmButton = {
                            Button(onClick = {
                                showPermissionDialog = false
                                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }) {
                                Text("Enable")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showPermissionDialog = false }) {
                                Text("Later")
                            }
                        }
                    )
                }

                Column {
                    NoInternetWarning(isConnected = isConnected.value)
                    AppNavigation(
                        navigateToChat = navigateToChat,
                        chatTitle = if (senderEmail.isNotEmpty()) "$chatTitle <$senderEmail>" else chatTitle
                    )
                }
            }
        }   
    }

    fun checkPlayServices() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            apiAvailability.getErrorDialog(this, resultCode, 9000)?.show()
        }
    }
}

