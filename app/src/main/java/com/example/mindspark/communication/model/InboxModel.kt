package com.example.mindspark.communication.model

import java.time.LocalDateTime

//data class Message(
//    val id: String,
//    val content: String,
//    val timestamp: LocalDateTime,
//    val isFromMe: Boolean,
//    val type: MessageType = MessageType.TEXT
//)
//
//enum class MessageType {
//    TEXT, IMAGE, FILE, VOICE
//}

enum class CallType {
    INCOMING, OUTGOING, MISSED, VIDEO
}

