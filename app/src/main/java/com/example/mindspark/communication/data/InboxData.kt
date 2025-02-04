package com.example.mindspark.communication.data

import com.example.mindspark.communication.model.CallModel
import com.example.mindspark.communication.model.CallType
import com.example.mindspark.communication.model.ChatModel
import java.util.UUID

val chatList = listOf(
    ChatModel(
        id = UUID.randomUUID().toString(),
        name = "Natasha",
        description = "Hi, Good Evening Bro.!",
        time = "14:59",
        messageCount = "03",
        isOnline = true
    ),
    ChatModel(
        id = UUID.randomUUID().toString(),
        name = "Alex",
        description = "I just Finished It.!",
        time = "06:35",
        messageCount = "02"
    ),
    // ... rest of your existing chat items ...
)

val callsList = listOf(
    CallModel(
        id = UUID.randomUUID().toString(),
        name = "Johan",
        type = CallType.INCOMING,
        date = "Feb 04, 2025"
    ),
    CallModel(
        id = UUID.randomUUID().toString(),
        name = "Timothee mathew",
        type = CallType.INCOMING,
        date = "Feb 04, 2025"
    ),
    // ... rest of your existing call items ...
)