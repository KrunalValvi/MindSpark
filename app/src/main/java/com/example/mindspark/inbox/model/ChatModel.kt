package com.example.mindspark.inbox.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatModel(
    val UserId: String = "",  // The Firestore document's user ID (must be non-empty)
    val ChatId: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val description: String = "",
    val messageCount: String = "0",  // Ensuring this is initialized as "0"
    val isOnline: Boolean = false,
    val profileImageUrl: String = "" // This can be a Base64 string or a URL
)

data class MessageModel(
    val id: String = "",
    val senderEmail: String = "",
    val receiverEmail: String = "",
    val messageText: String = "",
    val timestamp: Long = 0L,  // 🔹 Always store timestamp as Long
    val replyTo: String? = null,
    val replyToMessage: String? = null,
    val read: Boolean = false,  // Whether the message has been read
    val seen: Boolean = false   // Whether the message has been seen by the receiver
)

// Add this to your project
data class ChatStatus(
    val chatId: String = "",
    val userEmail: String = "",
    val unreadCount: Int = 0,
    val lastMessageTimestamp: Long = 0
)