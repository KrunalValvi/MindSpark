package com.example.mindspark.communication.data

import android.content.Context
import com.example.mindspark.communication.model.CallModel
import com.example.mindspark.communication.model.CallType
import com.example.mindspark.communication.model.ChatModel


val chatList = listOf(
    ChatModel(
        name = "Natasha",
        description = "Hi, Good Evening Bro.!",
        time = "14:59",
        messageCount = "03"
    ),
    ChatModel(
        name = "Alex",
        description = "I just Finished It.!",
        time = "06:35",
        messageCount = "02"
    ),
    ChatModel(
        name = "John",
        description = "How are you?",
        time = "08:10",
        messageCount = "54"
    ),
    ChatModel(
        name = "Mia",
        description = "OMG, This is Amazing..",
        time = "21:07",
        messageCount = "05"
    ),
    ChatModel(
        name = "Maria",
        description = "Wow, This is Really Epic",
        time = "09:15",
        messageCount = "23"
    ),
    ChatModel(
        name = "Tiya",
        description = "Hi, Good Evening Bro.!",
        time = "14:59",
        messageCount = "03"
    ),
    ChatModel(
        name = "Manisha",
        description = "I just Finished It.!",
        time = "06:35",
        messageCount = "02"
    ),
    ChatModel(
        name = "Beverly J. Barbee",
        description = "Perfect.!",
        time = "06:54",
        messageCount = "44"
    ),
    ChatModel(
        name = "Daniel",
        description = "See you soon!",
        time = "18:23",
        messageCount = "07"
    ),
    ChatModel(
        name = "Sophia",
        description = "Let's catch up later.",
        time = "11:45",
        messageCount = "19"
    ),
    ChatModel(
        name = "Elijah",
        description = "Haha, that’s funny!",
        time = "20:10",
        messageCount = "12"
    ),
    ChatModel(
        name = "Olivia",
        description = "Can we talk now?",
        time = "22:30",
        messageCount = "01"
    ),
    ChatModel(
        name = "Liam",
        description = "I’ll send it over.",
        time = "16:05",
        messageCount = "09"
    )
)

val callsList = listOf(
    CallModel(
        name = "Johan",
        type = CallType.INCOMING,
        date = "Nov 03, 202X"
    ),
    CallModel(
        name = "Timothee mathew",
        type = CallType.INCOMING,
        date = "Nov 05, 202X"
    ),
    CallModel(
        name = "Amanriya",
        type = CallType.OUTGOING,
        date = "Nov 06, 202X"
    ),
    CallModel(
        name = "Tanisha",
        type = CallType.MISSED,
        date = "Nov 15, 202X"
    ),
    CallModel(
        name = "Shravya",
        type = CallType.OUTGOING,
        date = "Nov 17, 202X"
    ),
    CallModel(
        name = "Tamanha",
        type = CallType.MISSED,
        date = "Nov 18, 202X"
    ),
    CallModel(
        name = "Hilda M. Hernandez",
        type = CallType.OUTGOING,
        date = "Nov 19, 202X"
    ),
    CallModel(
        name = "Wanda T. Seidl",
        type = CallType.INCOMING,
        date = "Nov 21, 202X"
    )
)
