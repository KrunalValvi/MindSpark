package com.example.mindspark.communication.model

data class ChatModel(
    val name: String,
    val description: String,
    val time: String,
    val messageCount: String,
)

//Call Screen
enum class CallType {
    INCOMING,
    OUTGOING,
    MISSED
}

data class CallModel(
    val name: String,
    val type: CallType,
    val date: String
)