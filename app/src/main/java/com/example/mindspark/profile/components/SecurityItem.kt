package com.example.mindspark.profile.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mindspark.ui.theme.customTypography

enum class SecuritySettingType {
    SWITCH, BUTTON, DROPDOWN
}

data class SecuritySetting(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val settingType: SecuritySettingType,
    val initialValue: Boolean = false,
    val dropdownOptions: List<String> = emptyList(),
    val onClick: () -> Unit = {}
)

@Composable
fun SecuritySection(
    title: String,
    icon: ImageVector,
    items: List<SecuritySetting>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1565C0)
                )
                Text(
                    text = title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 18.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items.forEach { setting ->
                    SecuritySettingItem(setting)
                    if (items.indexOf(setting) < items.lastIndex) {
                        Divider(color = Color(0xFFE0E0E0))
                    }
                }
            }
        }
    }
}

@Composable
fun SecuritySettingItem(setting: SecuritySetting) {
    var enabled by remember { mutableStateOf(setting.initialValue) }
    var selectedOption by remember { mutableStateOf(setting.dropdownOptions.firstOrNull() ?: "") }
    var showDropdown by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = setting.icon,
                contentDescription = null,
                tint = Color(0xFF1565C0),
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = setting.title,
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = setting.description,
                    style = MaterialTheme.customTypography.mulish.regular,
                    fontSize = 14.sp,
                    color = Color(0xFF6E7191)
                )
            }
        }

        when (setting.settingType) {
            SecuritySettingType.SWITCH -> {
                Switch(
                    checked = enabled,
                    onCheckedChange = { enabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF1565C0),
                        checkedTrackColor = Color(0xFFBBDEFB),
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color(0xFFE0E0E0)
                    )
                )
            }
            SecuritySettingType.BUTTON -> {
                TextButton(
                    onClick = setting.onClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF1565C0)
                    )
                ) {
                    Text("Change")
                }
            }
            SecuritySettingType.DROPDOWN -> {
                Box {
                    TextButton(
                        onClick = { showDropdown = true },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF1565C0)
                        )
                    ) {
                        Text(selectedOption)
                    }
                    DropdownMenu(
                        expanded = showDropdown,
                        onDismissRequest = { showDropdown = false }
                    ) {
                        setting.dropdownOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedOption = option
                                    showDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Change Password",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Current Password") },
                    visualTransformation = if (showCurrentPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                            Icon(
                                if (showCurrentPassword) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = if (showNewPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { showNewPassword = !showNewPassword }) {
                            Icon(
                                if (showNewPassword) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm New Password") },
                    visualTransformation = if (showConfirmPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                            Icon(
                                if (showConfirmPassword) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { onConfirm(currentPassword, newPassword) },
                        enabled = newPassword == confirmPassword &&
                                newPassword.isNotEmpty() &&
                                currentPassword.isNotEmpty()
                    ) {
                        Text("Change Password")
                    }
                }
            }
        }
    }
}