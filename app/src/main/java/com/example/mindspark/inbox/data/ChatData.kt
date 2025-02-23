package com.example.mindspark.inbox.data

import com.example.mindspark.inbox.model.ChatModel
import java.util.UUID

val chatList = listOf(
    ChatModel(
        id = UUID.randomUUID().toString(),
        name = "Mavji",
        description = "Hi, Good Evening Bro.!",
        time = "14:59",
        messageCount = "03",
        isOnline = true
    ),
    ChatModel(
        id = UUID.randomUUID().toString(),
        name = "Dhanji",
        description = "I just Finished It.!",
        time = "06:35",
        messageCount = "02"
    ),
    // ... rest of your existing chat items ...
)