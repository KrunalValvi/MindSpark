@file:Suppress("DEPRECATION")

package com.example.mindspark.community.ui

import android.text.format.DateUtils
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mindspark.community.data.CommunityViewModel
import com.example.mindspark.community.model.Post
import com.example.mindspark.ui.theme.LightBlueBackground
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController) {
    val viewModel: CommunityViewModel = viewModel()
    var isRefreshing by remember { mutableStateOf(false) }

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
        // Wrap your LazyColumn in SwipeRefresh
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.refreshPosts {
                    isRefreshing = false
                }
            }
        ) {
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
}

@Composable
fun PostCard(post: Post) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "${post.userName} • ${formatTimestamp(post.timestamp)}",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(
        timestamp,
        now,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    CommunityScreen(navController = NavController(LocalContext.current))
}
