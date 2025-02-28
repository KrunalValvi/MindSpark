package com.example.mindspark.community.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mindspark.community.data.CommunityViewModel
import com.example.mindspark.community.model.Post
import com.example.mindspark.ui.theme.LightBlueBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController) {
    // Obtain the view model from the current Composition
    val viewModel: CommunityViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightBlueBackground,
        topBar = {
            TopAppBar(title = { Text(text = "Community") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("NewPostScreen") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "New Post")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(paddingValues)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(viewModel.posts) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    // Simple UI to display a post
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(text = "${post.userName} • ${formatTimestamp(post.timestamp)}", style = MaterialTheme.typography.titleSmall)
        Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
    }
}

fun formatTimestamp(timestamp: Long): String {
    // Dummy formatter for now; replace with actual formatting
    return "2h ago"
}
