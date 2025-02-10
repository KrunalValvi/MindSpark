package com.example.mindspark.data.communication.data

import com.example.mindspark.data.communication.model.InboxModel

/**
 * InboxData.kt
 *
 * Handles data operations for the user's inbox (e.g., fetching and sending messages).
 */
class InboxData {
    private val messages = mutableListOf<InboxModel>()

    fun getInboxMessages(): List<InboxModel> = messages

    fun sendMessage(message: InboxModel) {
        messages.add(message)
    }
}
