package com.example.mindspark.inbox.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mindspark.inbox.components.ChatInputBar
import com.example.mindspark.inbox.model.ChatStatus
import com.example.mindspark.inbox.model.MessageModel
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.ServerValue
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

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
    var isLoading by remember { mutableStateOf(true) }

    // Store user's active chat state
    val userStatusRef = remember {
        FirebaseDatabase.getInstance().getReference("user_status")
    }

    // Function to mark messages as read
    fun markMessagesAsRead(chatId: String, currentUserEmail: String) {
        val sanitizedEmail = currentUserEmail.replace(".", "_")

        // Update the chat status to reset unread count
        val statusRef = FirebaseDatabase.getInstance().getReference("chatStatus")
            .child(sanitizedEmail)
            .child(chatId)

        statusRef.get().addOnSuccessListener { snapshot ->
            val currentStatus = snapshot.getValue(ChatStatus::class.java)
            statusRef.child("unreadCount").setValue(0)
        }

        // Mark individual messages as read and seen
        val messagesRef = FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId)
            .child("messages")

        messagesRef.orderByChild("receiverEmail").equalTo(sanitizedEmail)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (messageSnapshot in snapshot.children) {
                        if (messageSnapshot.child("read").getValue(Boolean::class.java) == false) {
                            messageSnapshot.ref.child("read").setValue(true)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("❌ Failed to update message read status: ${error.message}")
                }
            })

        // Mark messages sent by the current user as seen if they've been read by the receiver
        messagesRef.orderByChild("senderEmail").equalTo(sanitizedEmail)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (messageSnapshot in snapshot.children) {
                        if (messageSnapshot.child("read").getValue(Boolean::class.java) == true &&
                            messageSnapshot.child("seen").getValue(Boolean::class.java) == false
                        ) {
                            messageSnapshot.ref.child("seen").setValue(true)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("❌ Failed to update message seen status: ${error.message}")
                }
            })
    }

    // Function to delete message
    fun deleteMessage(chatId: String, messageId: String) {
        FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId)
            .child("messages")
            .child(messageId)
            .removeValue()
    }

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

    // Set active chat status when entering the screen
    LaunchedEffect(chatId) {
        isLoading = true

        // Set user's active chat status
        userStatusRef.child(sanitizedCurrentEmail).setValue(
            mapOf(
                "activeChatId" to chatId,
                "lastActive" to ServerValue.TIMESTAMP
            )
        )

        // Log that we're marking messages as read
        println("🔄 Marking messages as read on screen enter for chat: $chatId")

        markMessagesAsRead(chatId, currentUserEmail)
        val db =
            FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages")

        db.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessages =
                    snapshot.children.mapNotNull { it.getValue(MessageModel::class.java) }

                if (newMessages.isNotEmpty()) {
                    messages = newMessages.sortedBy { it.timestamp }
                    println("✅ Messages updated: ${messages.size}")

                    // Mark messages as read whenever new messages arrive
                    markMessagesAsRead(chatId, currentUserEmail)
                } else {
                    println("⚠️ No messages found")
                }
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                println("❌ Failed to load messages: ${error.message}")
                isLoading = false
            }
        })
    }

    // ✅ Fix: Prevent crashing when messages list is empty
    // Clear active chat status when leaving the screen
    DisposableEffect(chatId) {
        onDispose {
            userStatusRef.child(sanitizedCurrentEmail).setValue(null)
        }
    }

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
                    IconButton(onClick = {
                        // Make sure to mark messages as read before navigating back
                        markMessagesAsRead(chatId, currentUserEmail)
                        navController.navigateUp()
                    }) {
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
                        SendMessage(
                            chatId,
                            sanitizedCurrentEmail,
                            sanitizedReceiverEmail,
                            messageText,
                            replyingTo
                        )
                        messageText = ""
                        replyingTo = null
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightBlueBackground)
        ) {
            if (isLoading) {
                // Show loading indicator
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading messages...")
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    reverseLayout = false
                ) {
                    items(messages) { message ->
                        ChatBubble(
                            message = message,
                            currentUserEmail = currentUserEmail,
                            onLongPress = {
                                if (message.senderEmail.replace(
                                        ".",
                                        "_"
                                    ) == sanitizedCurrentEmail
                                ) {
                                    selectedMessageId = message.id
                                    showDeleteDialog = true
                                }
                            }
                        )
                        // Only show seen status for sent messages (not received)
                        if (message.senderEmail.replace(".", "_") == sanitizedCurrentEmail) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 8.dp, bottom = 8.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = if (message.seen) "Seen" else "Delivered",
                                    fontSize = 11.sp,
                                    color = if (message.seen) Color(0xFF2196F3) else Color.Gray
                                )
                            }
                        }
                    }
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
    val sanitizedCurrentEmail =
        currentUserEmail.replace(".", "_")  // Ensure consistency with chatId

    val isSender =
        message.senderEmail.replace(".", "_") == sanitizedCurrentEmail  // Compare sanitized emails

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

// Global reference to user status
private val userStatusRef = FirebaseDatabase.getInstance().getReference("user_status")

fun SendMessage(
    chatId: String,
    senderEmail: String,
    receiverEmail: String,
    messageText: String,
    replyTo: MessageModel? = null
) {
    try {
        val db = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages")
        val messageId = db.push().key ?: return

        val message = mapOf(
            "id" to messageId,
            "senderEmail" to senderEmail,
            "receiverEmail" to receiverEmail,
            "messageText" to messageText,
            "timestamp" to ServerValue.TIMESTAMP,
            "replyTo" to replyTo?.id,
            "replyToMessage" to replyTo?.messageText,
            "read" to false,  // Message initially unread
            "seen" to false   // Message initially unseen
        )

        // Get sender's name for notification
        val currentUser = FirebaseAuth.getInstance().currentUser
        val senderName = currentUser?.displayName ?: "Someone"

        // Get receiver's FCM token
        val receiverTokenRef = FirebaseDatabase.getInstance().getReference("fcm_tokens")
            .child(receiverEmail.replace(".", "_"))

        // Check if receiver is actively viewing this chat
        userStatusRef.child(receiverEmail.replace(".", "_")).get()
            .addOnSuccessListener { statusSnapshot ->
                val activeChat = statusSnapshot.child("activeChatId").getValue(String::class.java)
                val lastActive = statusSnapshot.child("lastActive").getValue(Long::class.java)
                val isReceiverActive = activeChat == chatId && lastActive != null &&
                        (System.currentTimeMillis() - lastActive < 60000) // Consider active if last active within 1 minute

                // Always get the receiver's token and prepare notification data
                receiverTokenRef.get().addOnSuccessListener { tokenSnapshot ->
                    val receiverToken = tokenSnapshot.getValue(String::class.java)
                    if (receiverToken != null) {
                        val notificationData = mapOf(
                            "title" to senderName,
                            "body" to messageText,
                            "chatPartner" to (currentUser?.email ?: ""),
                            "type" to "chat",
                            "token" to receiverToken
                        )

                        // Store notification in database regardless of user's active status
                        // This ensures notifications are sent even when the app is closed
                        val notificationsRef =
                            FirebaseDatabase.getInstance().getReference("notifications")
                                .child(receiverEmail.replace(".", "_"))
                                .push()

                        notificationsRef.setValue(
                            hashMapOf(
                                "token" to receiverToken,
                                "data" to notificationData,
                                "timestamp" to ServerValue.TIMESTAMP,
                                // Add a flag to indicate if user is active in chat
                                // The messaging service can use this to decide whether to show a notification
                                "isReceiverActive" to isReceiverActive
                            )
                        )
                    }
                }

                db.child(messageId).setValue(message)
                    .addOnSuccessListener {
                        println("✅ Message sent successfully: $message")

                        // Update unread count for the receiver
                        val statusDb = FirebaseDatabase.getInstance().getReference("chatStatus")
                            .child(receiverEmail.replace(".", "_"))
                            .child(chatId)

                        // Increment unread count for receiver
                        statusDb.runTransaction(object : Transaction.Handler {
                            override fun doTransaction(currentData: MutableData): Transaction.Result {
                                var chatStatus = currentData.getValue(ChatStatus::class.java)
                                    ?: ChatStatus(unreadCount = 0)
                                
                                chatStatus = chatStatus.copy(unreadCount = chatStatus.unreadCount + 1)
                                currentData.setValue(chatStatus)
                                return Transaction.success(currentData)
                            }

                            override fun onComplete(
                                error: DatabaseError?,
                                committed: Boolean,
                                currentData: DataSnapshot?
                            ) {
                                if (error != null) {
                                    println("❌ Failed to update unread count: ${error.message}")
                                } else {
                                    println("✅ Unread count updated successfully")
                                }
                            }
                        })
                    }
                    .addOnFailureListener { error ->
                        println("❌ Failed to send message: ${error.message}")
                    }
            }
    } catch (e: Exception) {
        println("❌ Failed to send message: ${e.message}")
    }
}


// Enhanced markMessagesAsRead function to update both read and seen status
private fun markMessagesAsRead(chatId: String, currentUserEmail: String) {
    val sanitizedEmail = currentUserEmail.replace(".", "_")

    // Debug log
    println("🔄 Marking messages as read for chat: $chatId, user: $sanitizedEmail")

    // Update the chat status to reset unread count
    val statusRef = FirebaseDatabase.getInstance().getReference("chatStatus")
        .child(sanitizedEmail)
        .child(chatId)

    statusRef.get().addOnSuccessListener { snapshot ->
        val currentStatus = snapshot.getValue(ChatStatus::class.java)
        println("📊 Current unread count: ${currentStatus?.unreadCount ?: 0}")

        statusRef.child("unreadCount").setValue(0)
            .addOnSuccessListener {
                println("✅ Messages marked as read")
            }
            .addOnFailureListener { error ->
                println("❌ Failed to mark messages as read: ${error.message}")
            }
    }

    // Mark individual messages as read and seen
    val messagesRef = FirebaseDatabase.getInstance().getReference("chats")
        .child(chatId)
        .child("messages")

    messagesRef.orderByChild("receiverEmail").equalTo(sanitizedEmail)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    if (messageSnapshot.child("read").getValue(Boolean::class.java) == false) {
                        messageSnapshot.ref.child("read").setValue(true)
                        println("✅ Marked message ${messageSnapshot.key} as read")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("❌ Failed to update message read status: ${error.message}")
            }
        })

    // Mark messages sent by the current user as seen if they've been read by the receiver
    messagesRef.orderByChild("senderEmail").equalTo(sanitizedEmail)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    if (messageSnapshot.child("read").getValue(Boolean::class.java) == true &&
                        messageSnapshot.child("seen").getValue(Boolean::class.java) == false
                    ) {
                        messageSnapshot.ref.child("seen").setValue(true)
                        println("✅ Marked message ${messageSnapshot.key} as seen")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("❌ Failed to update message seen status: ${error.message}")
            }
        })
}

suspend fun getLastMessage(chatId: String): String {
    return try {
        val messagesRef = FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId)
            .child("messages")
            .orderByChild("timestamp")
            .limitToLast(1)

        val dataSnapshot = messagesRef.get().await()
        if (dataSnapshot.exists()) {
            for (messageSnapshot in dataSnapshot.children) {
                val message = messageSnapshot.getValue(MessageModel::class.java)
                if (message != null) {
                    return message.messageText
                }
            }
        }
        return ""
    } catch (e: Exception) {
        println("❌ Error getting last message: ${e.message}")
        return ""
    }
}

private fun deleteMessage(chatId: String, messageId: String) {
    val db = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages")
        .child(messageId)
    db.removeValue()
}
