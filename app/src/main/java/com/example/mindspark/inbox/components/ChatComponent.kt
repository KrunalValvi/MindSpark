package com.example.mindspark.inbox.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mindspark.inbox.model.ChatModel
import com.example.mindspark.ui.theme.LightBlueBackground
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Decodes a Base64 string into a Bitmap.
 */
fun decodeBase64Image(base64String: String): Bitmap? {
    return try {
        val pureBase64Encoded = base64String.substringAfter(",") // Remove metadata if present
        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun ChatItem(
    chat: ChatModel,
    onChatClick: () -> Unit
) {
    // Debug log to track message count
    println("🔵 Checking message count for ${chat.fullName}: ${chat.messageCount}")

    val unreadCount = chat.messageCount.toIntOrNull() ?: 0
    val hasUnreadMessages = unreadCount > 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onChatClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val decodedImage = remember(chat.profileImageUrl) {
            decodeBase64Image(chat.profileImageUrl)
        }

        Box {
            if (decodedImage != null) {
                Image(
                    bitmap = decodedImage.asImageBitmap(),
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
            } else {
                AsyncImage(
                    model = if (chat.profileImageUrl.startsWith("http")) chat.profileImageUrl
                    else "https://api.dicebear.com/9.x/personas/png?seed=Joseph&size=256", // Default avatar
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
            }

            // Show green dot for online status
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                        .border(2.dp, Color.White, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = chat.fullName.ifEmpty { "Unknown User" },
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                )

                // Show unread indicator as a blue dot - Fixed condition
                if (hasUnreadMessages) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2196F3))
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chat.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(start = 8.dp)) {
            // Show message count badge - Fixed condition
            if (hasUnreadMessages) {
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    color = Color(0xFF2196F3)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = unreadCount.toString(),
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
fun ChatSection(
    chatList: List<ChatModel>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(chatList) { chat ->
            ChatItem(
                chat = chat,
                onChatClick = {
                    val encodedName = URLEncoder.encode(chat.fullName, StandardCharsets.UTF_8.toString())
                    val encodedEmail = URLEncoder.encode(chat.email, StandardCharsets.UTF_8.toString())

                    navController.navigate("ChatDetailScreen/$encodedName/$encodedEmail")
                }
            )
            if (chatList.indexOf(chat) < chatList.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatInputBarPreview() {
    ChatInputBar(
        message = "Hello",
        onMessageChange = {},
        onSendClick = {}
    )
}

@Composable
fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = LightBlueBackground,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = message,
                onValueChange = onMessageChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            if (message.isNotEmpty()) {
                IconButton(onClick = onSendClick) {
                    Icon(Icons.Default.Send, "Send")
                }
            }
        }
    }
}