package com.example.mindspark.communication.model

import java.time.LocalDateTime

data class ChatModel(
    val id: String = "",
    val name: String,
    val description: String,
    val time: String,
    val messageCount: String,
    val isOnline: Boolean = false
)

data class Message(
    val id: String,
    val content: String,
    val timestamp: LocalDateTime,
    val isFromMe: Boolean,
    val type: MessageType = MessageType.TEXT
)

enum class MessageType {
    TEXT, IMAGE, FILE, VOICE
}

enum class CallType {
    INCOMING, OUTGOING, MISSED, VIDEO
}

data class CallModel(
    val id: String = "",
    val name: String,
    val type: CallType,
    val date: String,
    val duration: String? = null
)