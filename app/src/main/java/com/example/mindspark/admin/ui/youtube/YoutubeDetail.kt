package com.example.mindspark.admin.ui.youtube

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.admin.model.AdminYoutubeModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeDetail(apiKey: String, navController: NavController) {
    var playlistUrl by remember { mutableStateOf("") }
    var playlistItems by remember { mutableStateOf<List<PlaylistItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // This state determines if we should show the edit form
    var showEditScreen by remember { mutableStateOf(false) }
    // Hold the model that will be edited. Prepopulate with fetched details.
    var adminYoutubeModel by remember { mutableStateOf(AdminYoutubeModel()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (!showEditScreen) {
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
                            if (playlistItems.isNotEmpty()) {
                                // Prepopulate some fields from fetched data:
                                // Use first item's title and thumbnail as defaults, and count the videos.
                                val firstItem = playlistItems.first()
                                adminYoutubeModel = AdminYoutubeModel(
                                    title = firstItem.snippet.title,
                                    imageRes = firstItem.snippet.thumbnails.medium.url,
                                    videos = playlistItems.size.toString()
                                    // Other fields remain blank; admin will fill them.
                                )
                                // Show the edit form within this screen
                                showEditScreen = true
                            } else {
                                errorMessage = "No items found or an error occurred."
                            }
                        }
                    } else {
                        errorMessage = "Invalid playlist URL."
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Fetch Playlist")
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        } else {
            AdminEditYoutubeScreen(
//                navController = navController,
                initialModel = adminYoutubeModel,
                playlistViews = playlistItems.size.toString(),
                playlistVideos = playlistItems.size,
                playlistDescription = playlistItems.firstOrNull()?.snippet?.description.orEmpty(),
                estimatedHours = playlistItems.size / 10, // Assuming 10 videos per hour
            ) { updatedModel ->
                saveAdminYoutubeModelToFirebase(updatedModel,
                    onSuccess = {
                        // Optionally navigate back or show a success message
                        showEditScreen = false
                        // For example, you might navigate to a confirmation screen:
                        navController.navigate("home")
                    },
                    onFailure = { error ->
                        // Handle error, for example show a snackbar or error message.
                        errorMessage = error.localizedMessage ?: "Error saving data."
                    }
                )
            }
        }
    }
}

fun saveAdminYoutubeModelToFirebase(
    model: AdminYoutubeModel,
    onSuccess: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
) {
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("courses")
        .add(model)
        .addOnSuccessListener { documentReference ->
            Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
            onSuccess()
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "Error adding document", e)
            onFailure(e)
        }
}
