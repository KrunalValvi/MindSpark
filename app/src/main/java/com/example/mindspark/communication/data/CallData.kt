package com.example.mindspark.communication.data

import com.example.mindspark.communication.model.CallModel
import com.example.mindspark.communication.model.CallType
import java.util.UUID

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