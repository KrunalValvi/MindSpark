package com.example.mindspark.inbox.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.inbox.components.ChatSection
import com.example.mindspark.inbox.model.ChatModel
import com.example.mindspark.ui.theme.LightBlueBackground
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun InboxScreen(navController: NavController) {
    var userList by remember { mutableStateOf<List<ChatModel>>(emptyList()) }
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUserEmail = auth.currentUser?.email

    LaunchedEffect(Unit) {
        try {
            val snapshot = db.collection("users").get().await()
            val users = snapshot.documents.mapNotNull { doc ->
                val user = doc.toObject(ChatModel::class.java)
                if(user != null && user.email != currentUserEmail) user else null
            }
            userList = users
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")  // Debug error
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            ChatSection(
                chatList = userList,
                navController = navController
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun InboxScreenPreview() {
    InboxScreen(navController = NavController(LocalContext.current))
}