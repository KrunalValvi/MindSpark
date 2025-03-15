package com.example.mindspark.admin.ui.youtube

import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(apiKey: String, navController: NavController) {
    var playlistUrl by remember { mutableStateOf("") }
    var playlistItems by remember { mutableStateOf<List<PlaylistItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = playlistUrl,
            onValueChange = { playlistUrl = it },
            label = { Text("Enter YouTube Playlist URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val playlistId = extractPlaylistId(playlistUrl)
                if (playlistId != null) {
                    isLoading = true
                    errorMessage = null
                    coroutineScope.launch {
                        playlistItems = fetchPlaylistItems(playlistId, apiKey)
                        isLoading = false
                        if (playlistItems.isEmpty()) {
                            errorMessage = "No items found or an error occurred."
                        }
                    }
                } else {
                    errorMessage = "Invalid playlist URL."
                }
            },
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
        ) {
            Text("Fetch Playlist")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(playlistItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                        // Handle item click, e.g., navigate to a video player screen
                    },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        AndroidView(factory = { context -> ImageView(context) }, update = {
                            Picasso.get().load(item.snippet.thumbnails.medium.url).into(it)
                        })
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = item.snippet.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = item.snippet.description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
