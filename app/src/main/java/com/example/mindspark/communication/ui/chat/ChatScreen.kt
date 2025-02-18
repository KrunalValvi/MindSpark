package com.example.mindspark.communication.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.communication.components.ChatInputBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavController,
    chatId: String
) {
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Start call */ }) {
                        Icon(Icons.Default.Call, "Call")
                    }
                    IconButton(onClick = { /* Show more options */ }) {
                        Icon(Icons.Default.MoreVert, "More")
                    }
                }
            )
        },
        bottomBar = {
            ChatInputBar(
                message = messageText,
                onMessageChange = { messageText = it },
                onSendClick = {
                    // Handle send message
                    messageText = ""
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            // Messages will be added here
        }
    }
}

