package com.example.mindspark.notifications.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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

        // Check if the current user is actively viewing this chat
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val sanitizedEmail = user.email?.replace(".", "_")
            sanitizedEmail?.let { email ->
                val userStatusRef = FirebaseDatabase.getInstance()
                    .getReference("user_status")
                    .child(email)

                userStatusRef.get().addOnSuccessListener { snapshot ->
                    val activeChatId = snapshot.child("activeChatId").getValue(String::class.java)
                    val lastActive = snapshot.child("lastActive").getValue(Long::class.java)
                    val chatPartner = remoteMessage.data["chatPartner"]
                    
                    // Only show notification if user is not actively viewing this chat
                    val isActiveInChat = activeChatId != null && 
                            chatPartner != null && 
                            activeChatId.contains(chatPartner.replace(".", "_")) &&
                            lastActive != null &&
                            (System.currentTimeMillis() - lastActive < 60000) // Within 1 minute

                    if (!isActiveInChat) {
                        // Show notification
                        val title = remoteMessage.data["title"] ?: "New Message"
                        val body = remoteMessage.data["body"] ?: ""
                        showNotification(title, body)
                    }
                }
            }
        }
    }

    private fun showNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

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
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}