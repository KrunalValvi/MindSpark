package com.example.mindspark.community.model

data class Post(
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val fullName: String = "",
    val profileImageUrl: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val category: String = "All",  // Added category field
    val timestamp: Long = System.currentTimeMillis(),
    val likes: Int = 0,
    val likedBy: List<String> = listOf(),
    val comments: List<Comment>? = listOf()  // Added comments list
)

data class Comment(
    val commentId: String = "",
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)