package com.example.mindspark.inbox.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mindspark.inbox.components.ChatInputBar
import com.example.mindspark.inbox.model.MessageModel
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    val navController = rememberNavController()
    ChatDetailScreen(navController, "John Doe", "john.mckinley@examplepetstore.com")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavController,
    fullName: String,
    receiverEmail: String
) {
    var messageText by remember { mutableStateOf("") }
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

    val sanitizedCurrentEmail = currentUserEmail.replace(".", "_")
    val sanitizedReceiverEmail = receiverEmail.replace(".", "_")
    val chatId = if (sanitizedCurrentEmail < sanitizedReceiverEmail)
        "$sanitizedCurrentEmail-$sanitizedReceiverEmail"
    else
        "$sanitizedReceiverEmail-$sanitizedCurrentEmail"

    var messages by remember { mutableStateOf<List<MessageModel>>(emptyList()) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedMessageId by remember { mutableStateOf<String?>(null) }
    var replyingTo by remember { mutableStateOf<MessageModel?>(null) }
    val listState = rememberLazyListState()

    LaunchedEffect(chatId) {
        val db = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages")

        db.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessages = snapshot.children.mapNotNull { it.getValue(MessageModel::class.java) }

                if (newMessages.isNotEmpty()) {
                    messages = newMessages.sortedBy { it.timestamp }
                    println("✅ Messages updated: ${messages.size}")
                } else {
                    println("⚠️ No messages found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("❌ Failed to load messages: ${error.message}")
            }
        })
    }



    // ✅ Fix: Prevent crashing when messages list is empty
    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightBlueBackground),
                title = { Text(fullName) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        bottomBar = {
            ChatInputBar(
                message = messageText,
                onMessageChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotEmpty()) {
                        sendMessage(chatId, sanitizedCurrentEmail, sanitizedReceiverEmail, messageText, replyingTo)
                        messageText = ""
                        replyingTo = null
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightBlueBackground)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                reverseLayout = false
            ) {
                items(messages) { message ->
                    ChatBubble(
                        message = message,
                        currentUserEmail = currentUserEmail,
                        onLongPress = {
                            if (message.senderEmail.replace(".", "_") == sanitizedCurrentEmail) {
                                selectedMessageId = message.id
                                showDeleteDialog = true
                            }
                        }
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Message") },
            text = { Text("Are you sure you want to delete this message?") },
            confirmButton = {
                Button(onClick = {
                    selectedMessageId?.let { deleteMessage(chatId, it) }
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatBubble(
    message: MessageModel,
    currentUserEmail: String,  // Pass the current user's email
    onLongPress: () -> Unit
) {
    val sanitizedCurrentEmail = currentUserEmail.replace(".", "_")  // Ensure consistency with chatId

    val isSender = message.senderEmail.replace(".", "_") == sanitizedCurrentEmail  // Compare sanitized emails

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = if (isSender) Color(0xFF2196F3) else Color.LightGray,
            modifier = Modifier
                .padding(4.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = onLongPress
                )
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                if (!message.replyToMessage.isNullOrEmpty()) {
                    Text(
                        text = "Reply: ${message.replyToMessage}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Text(
                    text = message.messageText,
                    style = MaterialTheme.customTypography.jost.semiBold.copy(fontSize = 16.sp),
                    color = if (isSender) Color.White else Color.Black
                )
            }
        }
    }
}



fun sendMessage(
    chatId: String,
    senderEmail: String,
    receiverEmail: String,
    messageText: String,
    replyTo: MessageModel? = null
) {
    val db = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages")
    val messageId = db.push().key ?: return

    val message = mapOf(
        "id" to messageId,
        "senderEmail" to senderEmail,
        "receiverEmail" to receiverEmail,
        "messageText" to messageText,
        "timestamp" to ServerValue.TIMESTAMP,
        "replyTo" to replyTo?.id,
        "replyToMessage" to replyTo?.messageText
    )

    db.child(messageId).setValue(message)
        .addOnSuccessListener {
            println("✅ Message sent successfully: $message")
        }
        .addOnFailureListener { error ->
            println("❌ Failed to send message: ${error.message}")
        }
}



fun deleteMessage(chatId: String, messageId: String) {
    val db = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages").child(messageId)
    db.removeValue()
}
