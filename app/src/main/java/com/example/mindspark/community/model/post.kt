package com.example.mindspark.community.model

data class Post(
    val postId: String = "",
    val fullName: String = "",
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
