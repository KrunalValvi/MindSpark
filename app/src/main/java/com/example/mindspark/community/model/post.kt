package com.example.mindspark.community.model

data class Post(
    val postId: String = "",        // Will be set when reading from Firestore
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
