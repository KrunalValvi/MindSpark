package com.example.mindspark.profile.ui.sections

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.profile.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(navController: NavController) {
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showBiometricSetup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Security & Privacy",
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF202244)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Password Section
            SecuritySection(
                title = "Password & Authentication",
                icon = Icons.Default.Lock,
                items = listOf(
                    SecuritySetting(
                        "Change Password",
                        "Last changed 30 days ago",
                        Icons.Default.Key,
                        settingType = SecuritySettingType.BUTTON
                    ) { showChangePasswordDialog = true },
                    SecuritySetting(
                        "Two-Factor Authentication",
                        "Add an extra layer of security",
                        Icons.Default.Security,
                        settingType = SecuritySettingType.SWITCH
                    ),
                    SecuritySetting(
                        "Remember Me",
                        "Stay signed in on this device",
                        Icons.Default.RememberMe,
                        settingType = SecuritySettingType.SWITCH,
                        initialValue = true
                    )
                )
            )

            // Biometric Section
            SecuritySection(
                title = "Biometric Security",
                icon = Icons.Default.Fingerprint,
                items = listOf(
                    SecuritySetting(
                        "Fingerprint Login",
                        "Use your fingerprint to sign in",
                        Icons.Default.Fingerprint,
                        settingType = SecuritySettingType.SWITCH
                    ),
                    SecuritySetting(
                        "Face ID",
                        "Use Face recognition to sign in",
                        Icons.Default.Face,
                        settingType = SecuritySettingType.SWITCH
                    )
                )
            )

            // Privacy Section
            SecuritySection(
                title = "Privacy",
                icon = Icons.Default.PrivacyTip,
                items = listOf(
                    SecuritySetting(
                        "Activity Status",
                        "Show when you're active",
                        Icons.Default.Visibility,
                        settingType = SecuritySettingType.SWITCH,
                        initialValue = true
                    ),
                    SecuritySetting(
                        "Profile Visibility",
                        "Control who can see your profile",
                        Icons.Default.Person,
                        settingType = SecuritySettingType.DROPDOWN,
                        dropdownOptions = listOf("Everyone", "Students Only", "Private")
                    )
                )
            )
        }
    }

    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showChangePasswordDialog = false },
            onConfirm = { currentPassword, newPassword ->
                // Handle password change
                showChangePasswordDialog = false
            }
        )
    }
}