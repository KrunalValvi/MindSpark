package com.example.mindspark.notifications.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Wallet
import com.example.mindspark.notifications.model.Notification

val todayNotifications = listOf(
    Notification(
        title = "New Category Course.!",
        message = "New the 3D Design Course is Available.",
        icon = Icons.Default.QrCode
    ),
    Notification(
        title = "Today's Special Offers",
        message = "You have made a Course Payment.",
        icon = Icons.Default.ConfirmationNumber
    )
)

val yesterdayNotifications = listOf(
    Notification(
        title = "Credit Card Connected.!",
        message = "Credit Card has been Linked.!",
        icon = Icons.Default.Wallet
    )
)

val olderNotifications = listOf(
    Notification(
        title = "Account Setup Successful.!",
        message = "Your Account has been Created.",
        icon = Icons.Default.Person
    )
)
