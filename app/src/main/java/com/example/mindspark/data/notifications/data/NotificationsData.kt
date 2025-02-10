package com.example.mindspark.data.notifications.data

import com.example.mindspark.data.notifications.model.NotificationsModel

/**
 * NotificationsData.kt
 *
 * Handles data operations for notifications.
 */
class NotificationsData {
    private val notifications = listOf(
        NotificationsModel("1", "Reminder", "Your course starts soon!"),
        NotificationsModel("2", "Offer", "New special offer available!")
    )

    fun getNotifications(): List<NotificationsModel> = notifications
}
