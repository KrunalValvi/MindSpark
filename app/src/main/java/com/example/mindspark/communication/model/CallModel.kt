package com.example.mindspark.communication.model

data class CallModel(
    val id: String = "",
    val name: String,
    val type: CallType,
    val date: String,
    val duration: String? = null
)