package com.example.mindspark.notifications.model

import androidx.compose.ui.graphics.vector.ImageVector

//Notifications Screen
data class Notification(
    val title: String,
    val message: String,
    val icon: ImageVector
)