package com.example.mindspark.communication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.communication.components.CallsSection
import com.example.mindspark.communication.components.ChatSection
import com.example.mindspark.communication.data.callsList
import com.example.mindspark.communication.data.chatList
import com.example.mindspark.courses.components.ToggleSelectionRowCourses
import com.example.mindspark.ui.theme.LightBlueBackground

@Composable
fun InboxScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf("Chat") }

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
            Spacer(modifier = Modifier.height(16.dp))

            ToggleSelectionRowCourses(
                options = listOf("Chat", "Calls"),
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedOption) {
                "Chat" -> {
                    ChatSection(
                        chatList = chatList,
                        navController = navController
                    )
                }
                "Calls" -> {
                    CallsSection(
                        callsList = callsList,
                        navController = navController
                    )
                }
            }
        }
    }
}