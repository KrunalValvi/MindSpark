//package com.example.mindspark.home.ui
//
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.Send
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import com.example.mindspark.R
//import com.google.ai.client.generativeai.GenerativeModel
//import com.google.ai.client.generativeai.type.generationConfig
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//// Chat message data class
//data class ChatMessage(
//    val content: String,
//    val isFromUser: Boolean,
//    val timestamp: Long = System.currentTimeMillis()
//)
//
//// Gemini Chat Service
//class GeminiChatService(private val apiKey: String) {
//    private val generativeModel by lazy {
//        GenerativeModel(
//            modelName = "gemini-pro",
//            apiKey = apiKey,
//            generationConfig = generationConfig {
//                temperature = 0.7f
//                topK = 40
//                topP = 0.95f
//                maxOutputTokens = 1000
//            }
//        )
//    }
//
//    suspend fun generateResponse(prompt: String): String {
//        return try {
//            withContext(Dispatchers.IO) {
//                val response = generativeModel.generateContent(prompt)
//                response.text ?: "Sorry, I couldn't generate a response."
//            }
//        } catch (e: Exception) {
//            Log.e("GeminiChatService", "Error generating response", e)
//            "Sorry, there was an error processing your request: ${e.localizedMessage}"
//        }
//    }
//}
//
//// ChatViewModel
//class ChatViewModel(private val chatService: GeminiChatService) {
//    var messages = mutableStateOf<List<ChatMessage>>(listOf())
//    var isLoading = mutableStateOf(false)
//    var error = mutableStateOf<String?>(null)
//
//    suspend fun sendMessage(userMessage: String) {
//        try {
//            // Add user message
//            val userChatMessage = ChatMessage(content = userMessage, isFromUser = true)
//            messages.value = messages.value + userChatMessage
//
//            // Show loading state
//            isLoading.value = true
//            error.value = null
//
//            // Get AI response
//            val response = chatService.generateResponse(userMessage)
//
//            // Add AI response
//            val aiChatMessage = ChatMessage(content = response, isFromUser = false)
//            messages.value = messages.value + aiChatMessage
//        } catch (e: Exception) {
//            Log.e("ChatViewModel", "Error in sending message", e)
//            error.value = "Failed to get response: ${e.localizedMessage}"
//        } finally {
//            isLoading.value = false
//        }
//    }
//}
//
//// FloatingChatButton to add to HomeScreen
//@Composable
//fun FloatingChatButton() {
//    // Get API key from resources or secure storage
//    val context = LocalContext.current
//    val apiKey = remember { getGeminiApiKey(context) }
//    val chatService = remember { GeminiChatService(apiKey) }
//    val viewModel = remember { ChatViewModel(chatService) }
//
//    var showChatDialog by remember { mutableStateOf(false) }
//
//    // Floating Action Button
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        contentAlignment = Alignment.BottomEnd
//    ) {
//        FloatingActionButton(
//            onClick = { showChatDialog = true },
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = Color.White
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_chat),
//                contentDescription = "Chat with AI",
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//
//    // Chat Dialog
//    if (showChatDialog) {
//        ChatDialog(
//            viewModel = viewModel,
//            onDismiss = { showChatDialog = false }
//        )
//    }
//}
//
//// Utility function to get API key
//private fun getGeminiApiKey(context: Context): String {
//    // In a real app, you would store this securely
//    // For this example, we assume it's stored in string resources
//    return context.getString(R.string.gemini_api_key)
//}
//
//// Chat Dialog UI
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatDialog(
//    viewModel: ChatViewModel,
//    onDismiss: () -> Unit
//) {
//    val messages = viewModel.messages.value
//    val isLoading = viewModel.isLoading.value
//    val error = viewModel.error.value
//
//    var userInput by remember { mutableStateOf("") }
//    val listState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//
//    // Scroll to bottom when new messages arrive
//    LaunchedEffect(messages.size) {
//        if (messages.isNotEmpty()) {
//            listState.animateScrollToItem(messages.size - 1)
//        }
//    }
//
//    Dialog(
//        onDismissRequest = onDismiss,
//        properties = DialogProperties(
//            dismissOnBackPress = true,
//            dismissOnClickOutside = true,
//            usePlatformDefaultWidth = false
//        )
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xF5F9FF).copy(alpha = 0.95f))
//                .padding(16.dp)
//        ) {
//            Card(
//                modifier = Modifier
//                    .fillMaxSize(),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = Color.White
//                ),
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 6.dp
//                )
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                ) {
//                    // Header
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .size(40.dp)
//                                    .clip(CircleShape)
//                                    .background(MaterialTheme.colorScheme.primary),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_ai),
//                                    contentDescription = "AI Assistant",
//                                    tint = Color.White,
//                                    modifier = Modifier.size(24.dp)
//                                )
//                            }
//                            Spacer(modifier = Modifier.padding(8.dp))
//                            Text(
//                                text = "MindSpark AI Assistant",
//                                style = MaterialTheme.typography.titleMedium,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                        IconButton(onClick = onDismiss) {
//                            Icon(
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "Close Chat"
//                            )
//                        }
//                    }
//
//                    // Messages
//                    LazyColumn(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxWidth(),
//                        state = listState
//                    ) {
//                        items(messages) { message ->
//                            ChatMessageItem(message = message)
//                            Spacer(modifier = Modifier.height(8.dp))
//                        }
//
//                        // Welcome message if no messages
//                        if (messages.isEmpty()) {
//                            item {
//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(vertical = 4.dp, horizontal = 8.dp),
//                                    colors = CardDefaults.cardColors(
//                                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
//                                    ),
//                                    shape = RoundedCornerShape(12.dp)
//                                ) {
//                                    Column(
//                                        modifier = Modifier.padding(12.dp)
//                                    ) {
//                                        Text(
//                                            text = "👋 Hello! I'm your MindSpark AI Assistant.",
//                                            style = MaterialTheme.typography.bodyLarge,
//                                            fontWeight = FontWeight.Medium
//                                        )
//                                        Spacer(modifier = Modifier.height(4.dp))
//                                        Text(
//                                            text = "How can I help you with your learning today?",
//                                            style = MaterialTheme.typography.bodyMedium
//                                        )
//                                    }
//                                }
//                            }
//                        }
//
//                        // Error message
//                        if (error != null) {
//                            item {
//                                Text(
//                                    text = error,
//                                    color = MaterialTheme.colorScheme.error,
//                                    modifier = Modifier.padding(8.dp)
//                                )
//                            }
//                        }
//
//                        // Loading indicator
//                        if (isLoading) {
//                            item {
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(8.dp),
//                                    horizontalArrangement = Arrangement.Start
//                                ) {
//                                    CircularProgressIndicator(
//                                        modifier = Modifier.size(24.dp),
//                                        color = MaterialTheme.colorScheme.primary,
//                                        strokeWidth = 2.dp
//                                    )
//                                    Spacer(modifier = Modifier.padding(8.dp))
//                                    Text(
//                                        text = "Thinking...",
//                                        style = MaterialTheme.typography.bodyMedium,
//                                        color = MaterialTheme.colorScheme.primary
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Input field
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        OutlinedTextField(
//                            value = userInput,
//                            onValueChange = { userInput = it },
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(end = 8.dp),
//                            placeholder = {
//                                Text("Type your message...")
//                            },
//                            shape = RoundedCornerShape(24.dp),
//                            colors = TextFieldDefaults.outlinedTextFieldColors(
//                                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
//                            ),
//                            maxLines = 3
//                        )
//
//                        IconButton(
//                            onClick = {
//                                if (userInput.isNotBlank() && !isLoading) {
//                                    val message = userInput.trim()
//                                    userInput = ""
//                                    coroutineScope.launch {
//                                        viewModel.sendMessage(message)
//                                    }
//                                }
//                            },
//                            modifier = Modifier
//                                .clip(CircleShape)
//                                .background(
//                                    if (userInput.isNotBlank() && !isLoading)
//                                        MaterialTheme.colorScheme.primary
//                                    else
//                                        Color.Gray.copy(alpha = 0.5f)
//                                )
//                                .size(48.dp),
//                            enabled = userInput.isNotBlank() && !isLoading
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Send,
//                                contentDescription = "Send",
//                                tint = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// Individual message item
//@Composable
//fun ChatMessageItem(message: ChatMessage) {
//    val bubbleColor = if (message.isFromUser)
//        MaterialTheme.colorScheme.primary
//    else
//        Color(0xFFE8F0FE)
//
//    val textColor = if (message.isFromUser)
//        Color.White
//    else
//        Color.Black
//
//    val alignment = if (message.isFromUser)
//        Alignment.End
//    else
//        Alignment.Start
//
//    val bubbleShape = if (message.isFromUser)
//        RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp)
//    else
//        RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp)
//
//    Box(
//        modifier = Modifier.fillMaxWidth(),
//        contentAlignment = alignment
//    ) {
//        Card(
//            shape = bubbleShape,
//            colors = CardDefaults.cardColors(
//                containerColor = bubbleColor
//            ),
//            modifier = Modifier
//                .padding(4.dp)
//                .fillMaxWidth(0.8f)
//        ) {
//            Text(
//                text = message.content,
//                color = textColor,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(12.dp)
//            )
//        }
//    }
//}
//
//// Add this to the resource files if it doesn't exist:
//// In res/drawable/ic_chat.xml - Use vector asset for chat icon
//// In res/drawable/ic_ai.xml - Use vector asset for AI icon
//// In res/values/strings.xml - Add <string name="gemini_api_key">YOUR_API_KEY</string>