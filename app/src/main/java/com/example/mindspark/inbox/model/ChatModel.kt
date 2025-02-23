package com.example.mindspark.inbox.model

data class ChatModel(
    val id: String = "",
    val name: String,
    val description: String,
    val time: String,
    val messageCount: String,
    val isOnline: Boolean = false
)