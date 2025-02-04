package com.example.mindspark.communication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.communication.model.CallModel
import com.example.mindspark.communication.model.CallType
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CallItem(
    call: CallModel,
    onCallClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCallClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture
        Surface(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            color = Color.LightGray
        ) { }

        Spacer(modifier = Modifier.width(12.dp))

        // Call Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = call.name,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val icon = when (call.type) {
                    CallType.INCOMING -> Icons.AutoMirrored.Filled.CallReceived
                    CallType.OUTGOING -> Icons.AutoMirrored.Filled.CallMade
                    CallType.MISSED -> Icons.AutoMirrored.Filled.CallMissed
                    CallType.VIDEO -> Icons.Default.Videocam
                }

                val color = when (call.type) {
                    CallType.MISSED -> Color.Red
                    CallType.VIDEO -> Color(0xFF1565C0)
                    else -> Color(0xFF4CAF50)
                }

                val typeText = when (call.type) {
                    CallType.INCOMING -> "Incoming"
                    CallType.OUTGOING -> "Outgoing"
                    CallType.MISSED -> "Missed"
                    CallType.VIDEO -> "Video Call"
                }

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$typeText | ${call.date}",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        // Call Actions
        Row {
            // Video Call Button
            IconButton(
                onClick = { /* Start video call */ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = "Video Call",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Voice Call Button
            IconButton(onClick = { onCallClick() }) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun CallsSection(callsList: List<CallModel>, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Call stats
        CallStats()

        // Calls list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(callsList) { call ->
                CallItem(
                    call = call,
                    onCallClick = {
                        // Navigate to active call screen
                        navController.navigate("ActiveCallScreen/${call.id}")
                    }
                )
                if (callsList.indexOf(call) < callsList.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun CallStats() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CallStatItem(
                icon = Icons.AutoMirrored.Filled.CallReceived,
                count = "12",
                label = "Incoming",
                color = Color(0xFF4CAF50)
            )
            CallStatItem(
                icon = Icons.AutoMirrored.Filled.CallMade,
                count = "8",
                label = "Outgoing",
                color = Color(0xFF1565C0)
            )
            CallStatItem(
                icon = Icons.AutoMirrored.Filled.CallMissed,
                count = "3",
                label = "Missed",
                color = Color.Red
            )
        }
    }
}

@Composable
fun CallStatItem(
    icon: ImageVector,
    count: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = count,
            style = MaterialTheme.customTypography.jost.bold,
            fontSize = 18.sp,
            color = Color(0xFF202244)
        )
        Text(
            text = label,
            style = MaterialTheme.customTypography.mulish.regular,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
