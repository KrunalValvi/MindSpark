package com.example.mindspark.communication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.communication.model.CallModel
import com.example.mindspark.communication.model.CallType
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CallItem(
    name: String,
    callType: CallType,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture Placeholder - matched size with chat
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
                text = name,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val icon = when (callType) {
                    CallType.INCOMING -> Icons.Filled.CallReceived
                    CallType.OUTGOING -> Icons.Filled.CallMade
                    CallType.MISSED -> Icons.Filled.CallMissed
                }

                val color = when (callType) {
                    CallType.MISSED -> Color.Red
                    else -> Color(0xFF4CAF50)
                }

                val typeText = when (callType) {
                    CallType.INCOMING -> "Incoming"
                    CallType.OUTGOING -> "Outgoing"
                    CallType.MISSED -> "Missed"
                }

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$typeText | $date",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        // Call Button - aligned with chat UI spacing
        IconButton(
            onClick = { /* Handle call action */ },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Call,
                contentDescription = "Call",
                tint = Color(0xFF2196F3),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CallsSection(callsList: List<CallModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(callsList) { call ->
            CallItem(
                name = call.name,
                callType = call.type,
                date = call.date
            )
            if (callsList.indexOf(call) < callsList.lastIndex) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChatSectionPrevie2w() {
//    CallsSection(callsList)
//}