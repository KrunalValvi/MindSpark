package com.example.mindspark.data.communication.model

/**
 * InboxModel.kt
 *
 * Model representing a message in the user's inbox.
 */
data class InboxModel(
    val id: String,
    val sender: String,
    val receiver: String,
    val message: String,
    val timestamp: Long  // Unix timestamp when the message was sent
)
