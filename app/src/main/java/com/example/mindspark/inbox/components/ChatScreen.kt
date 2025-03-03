package com.example.mindspark.inbox.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.inbox.model.ChatModel
import com.example.mindspark.inbox.ui.InboxScreen
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ChatItem(
    chat: ChatModel,
    onChatClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onChatClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            color = Color.LightGray
        ) { }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.fullName.ifEmpty { "Unknown User" },  // Show fallback name
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.description,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            if (chat.messageCount != "0") {
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    color = Color(0xFF2196F3)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = chat.messageCount,
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
//                userId = chat.UserId, // Ensure userId is provided as a String
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
//            IconButton(onClick = { /* Open attachment */ }) {
//                Icon(Icons.Default.AttachFile, "Attach")
//            }
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
