package com.example.mindspark.inbox.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.inbox.components.ChatSection
import com.example.mindspark.inbox.model.ChatModel
import com.example.mindspark.inbox.model.ChatStatus
import com.example.mindspark.ui.theme.LightBlueBackground
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun InboxScreen(navController: NavController) {
    var userList by remember { mutableStateOf<List<ChatModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUserEmail = auth.currentUser?.email ?: ""
    val sanitizedCurrentEmail = currentUserEmail.replace(".", "_")

    LaunchedEffect(Unit) {
        try {
            // Set loading state to true
            isLoading = true

            // Load users
            val snapshot = db.collection("users").get().await()
            val users = snapshot.documents.mapNotNull { doc ->
                val user = doc.toObject(ChatModel::class.java)
                if (user != null && user.email != currentUserEmail) user else null
            }

            // Create a mutable list to update with unread counts and last messages
            val usersWithCountsAndMessages = mutableListOf<ChatModel>()

            // For each user, fetch the last message
            for (user in users) {
                val sanitizedUserEmail = user.email.replace(".", "_")
                val chatId = if (sanitizedCurrentEmail < sanitizedUserEmail)
                    "$sanitizedCurrentEmail-$sanitizedUserEmail"
                else
                    "$sanitizedUserEmail-$sanitizedCurrentEmail"

                val lastMessage = getLastMessage(chatId)
                usersWithCountsAndMessages.add(
                    user.copy(description = if (lastMessage.isNotEmpty()) lastMessage else "No messages yet")
                )
            }

            // Update the list with users that have last messages
            userList = usersWithCountsAndMessages.toList()

            // Set loading state to false after initial data load
            isLoading = false

            // Reference to the chat status database to listen for unread counts
            val statusRef = FirebaseDatabase.getInstance().getReference("chatStatus")
                .child(sanitizedCurrentEmail)

            // Listen for chat status updates
            statusRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val updatedUsers = userList.toMutableList()

                    for (chatStatusSnapshot in snapshot.children) {
                        val chatStatus = chatStatusSnapshot.getValue(ChatStatus::class.java) ?: continue
                        val chatId = chatStatus.chatId

                        // Find the corresponding user
                        val otherEmail = if (chatId.startsWith(sanitizedCurrentEmail)) {
                            chatId.substringAfter("-")
                        } else {
                            chatId.substringBefore("-")
                        }.replace("_", ".")

                        // Update the user's message count
                        val userIndex = updatedUsers.indexOfFirst { it.email == otherEmail }
                        if (userIndex != -1) {
                            val user = updatedUsers[userIndex]
                            updatedUsers[userIndex] = user.copy(
                                messageCount = chatStatus.unreadCount.toString()
                            )
                        }
                    }

                    // Update the state with the new list
                    userList = updatedUsers.toList()
                }

                override fun onCancelled(error: DatabaseError) {
                    println("❌ Failed to load chat status: ${error.message}")
                }
            })
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
            isLoading = false
        }
    }
    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Inbox",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            if (isLoading) {
                // Show loading indicator
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading your conversations...")
                }
            } else {
                // Show chat list
                ChatSection(
                    chatList = userList,
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InboxScreenPreview() {
    InboxScreen(navController = NavController(LocalContext.current))
}