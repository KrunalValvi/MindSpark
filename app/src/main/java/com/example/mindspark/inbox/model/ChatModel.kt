package com.example.mindspark.inbox.model


data class ChatModel(
    val UserId: String = "",  // Firestore usually stores IDs as Strings
    val ChatId: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val description: String = "",
    val messageCount: String = "0",
    val isOnline: Boolean = false,
    val profileImageUrl: String = "" // Add this field
)

data class MessageModel(
    val id: String = "",
    val senderEmail: String = "",
    val receiverEmail: String = "",
    val messageText: String = "",
    val timestamp: Long = 0L,  // 🔹 Always store timestamp as Long
    val replyTo: String? = null,
    val replyToMessage: String? = null
)



