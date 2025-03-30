package com.example.mindspark.community.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mindspark.community.model.Comment
import com.example.mindspark.community.model.Post

class CommunityViewModel : ViewModel() {
    private val repository = PostRepository()

    // Holds the list of posts as a mutable state.
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set

    init {
        // Start listening for real-time updates
        repository.observePosts { updatedPosts ->
            posts = updatedPosts
        }
    }

    // Function to add a new post
    fun addNewPost(
        content: String,
        userId: String,
        userName: String,
        category: String = "All",
        onResult: (Boolean) -> Unit
    ) {
        val newPost = Post(
            userId = userId,
            userName = userName,
            content = content,
            category = category,
            timestamp = System.currentTimeMillis()
        )
        repository.addPost(newPost,
            onSuccess = { onResult(true) },
            onFailure = { onResult(false) }
        )
    }

    // Add a comment to a post
    fun addComment(postId: String, comment: Comment, onComplete: () -> Unit) {
        repository.addComment(postId, comment,
            onSuccess = { onComplete() },
            onFailure = { onComplete() }
        )
    }

    // Refresh posts (for pull-to-refresh)
    fun refreshPosts(onComplete: () -> Unit) {
        repository.getPosts(
            onSuccess = { fetchedPosts ->
                posts = fetchedPosts
                onComplete()
            },
            onFailure = {
                // Optionally handle the error
                onComplete()
            }
        )
    }
}