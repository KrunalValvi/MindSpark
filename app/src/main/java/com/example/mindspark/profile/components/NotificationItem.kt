package com.example.mindspark.profile.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography

data class NotificationSetting(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun NotificationMasterSwitch(
    enabled: Boolean,
    onEnabledChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Enable Notifications",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Control all notification settings",
                    style = MaterialTheme.customTypography.mulish.regular,
                    fontSize = 14.sp,
                    color = Color(0xFF6E7191)
                )
            }
            Switch(
                checked = enabled,
                onCheckedChange = onEnabledChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF1565C0),
                    checkedTrackColor = Color(0xFFBBDEFB),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color(0xFFE0E0E0)
                )
            )
        }
    }
}

@Composable
fun NotificationSection(
    title: String,
    icon: ImageVector,
    items: List<NotificationSetting>
) {
    var expanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items.forEach { setting ->
                        NotificationSettingItem(setting)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationSettingItem(setting: NotificationSetting) {
    var enabled by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
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
}