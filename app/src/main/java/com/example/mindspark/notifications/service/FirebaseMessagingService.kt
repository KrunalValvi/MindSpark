package com.example.mindspark.notifications.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mindspark.MainActivity
import com.example.mindspark.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MindSparkMessagingService : FirebaseMessagingService() {
    private val CHANNEL_ID = "chat_notifications"
    private val NOTIFICATION_ID = 100

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Store the new FCM token in Firebase Database
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val sanitizedEmail = user.email?.replace(".", "_")
            sanitizedEmail?.let { email ->
                FirebaseDatabase.getInstance()
                    .getReference("fcm_tokens")
                    .child(email)
                    .setValue(token)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Extract notification data
        val title = remoteMessage.data["title"] ?: "New Message"
        val body = remoteMessage.data["body"] ?: "" 
        val chatPartner = remoteMessage.data["chatPartner"]
        val senderName = remoteMessage.data["senderName"] ?: chatPartner ?: "Someone"
        val isReceiverActiveStr = remoteMessage.data["isReceiverActive"]
        val senderEmail = chatPartner ?: ""
        
        // If we have explicit information that receiver is active in chat, respect that
        if (isReceiverActiveStr == "true") {
            // User is active in this chat, don't show notification
            return
        }
        
        // For all other cases (app in background, closed, or no active status info)
        // Check if the current user is actively viewing this chat
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val sanitizedEmail = currentUser.email?.replace(".", "_")
            if (sanitizedEmail != null) {
                val userStatusRef = FirebaseDatabase.getInstance()
                    .getReference("user_status")
                    .child(sanitizedEmail)

                userStatusRef.get().addOnSuccessListener { snapshot ->
                    val activeChatId = snapshot.child("activeChatId").getValue(String::class.java)
                    val lastActive = snapshot.child("lastActive").getValue(Long::class.java)
                    
                    // Only show notification if user is not actively viewing this chat
                    val isActiveInChat = activeChatId != null && 
                            chatPartner != null && 
                            activeChatId.contains(chatPartner.replace(".", "_")) &&
                            lastActive != null &&
                            (System.currentTimeMillis() - lastActive < 60000) // Within 1 minute

                    if (!isActiveInChat) {
                        // Show notification
                        showNotification("$senderName sent you a message", body, senderEmail)
                    }
                }.addOnFailureListener {
                    // If we can't determine active status, show notification anyway
                    showNotification("$senderName sent you a message", body, senderEmail)
                }
            } else {
                // If we can't determine user email, show notification
                showNotification("$senderName sent you a message", body, senderEmail)
            }
        } else {
            // If user is not logged in, show notification
            showNotification("$senderName sent you a message", body, senderEmail)
        }
    }

    private fun showNotification(title: String, message: String, senderEmail: String = "") {
        // Create an intent that will open the app and navigate to the specific chat
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            // Add data to navigate to the specific chat
            putExtra("navigate_to_chat", true)
            putExtra("chat_title", title)
            putExtra("chat_message", message)
            putExtra("sender_email", senderEmail)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Chat Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for new chat messages"
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 250, 250)
                setShowBadge(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}