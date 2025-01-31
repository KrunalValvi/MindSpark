package com.example.mindspark.communication.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.communication.data.callsList
import com.example.mindspark.communication.data.chatList
import com.example.mindspark.communication.model.ChatModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun ChatItem(
    name: String,
    message: String,
    time: String,
    unreadCount: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),  // Increased vertical padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture Placeholder - increased size
        Surface(
            modifier = Modifier
                .size(48.dp)  // Increased from previous size
                .clip(CircleShape),
            color = Color.LightGray
        ) { }

        Spacer(modifier = Modifier.width(12.dp))  // Increased spacing

        // Message Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        // Time and Unread Count
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(start = 8.dp)  // Added padding
        ) {
            Text(
                text = time,
                style = MaterialTheme.customTypography.mulish.extraBold,
                fontSize = 11.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (unreadCount != null) {
                Surface(
                    modifier = Modifier
                        .size(24.dp),  // Increased badge size
                    shape = CircleShape,
                    color = Color(0xFF2196F3)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = unreadCount,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatSection(chatList: List<ChatModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)  // Added padding at top and bottom
    ) {
        items(chatList) { chat ->
            ChatItem(
                name = chat.name,
                message = chat.description,
                time = chat.time,
                unreadCount = chat.messageCount
            )
            if (chatList.indexOf(chat) < chatList.lastIndex) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatSectionPreview() {
    ChatSection(chatList)
}