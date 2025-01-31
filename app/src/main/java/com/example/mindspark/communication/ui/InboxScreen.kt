package com.example.mindspark.communication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
    val context = LocalContext.current

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
                    ChatSection(chatList)
                }

                "Calls" -> {
                    CallsSection(callsList)

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InboxScreenPreview() {
    InboxScreen(navController = NavController(LocalContext.current))
}
