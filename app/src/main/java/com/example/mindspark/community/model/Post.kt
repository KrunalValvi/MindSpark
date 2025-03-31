package com.example.mindspark.community.model

data class Post(
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val fullName: String = "",
    val profileImageUrl: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val category: String = "All",
    val timestamp: Long = System.currentTimeMillis(),
    val likes: Int = 0,
    val likedBy: List<String> = listOf(),
    val comments: List<Comment>? = listOf()
)

data class Comment(
    val commentId: String = "",  // This field needs to be initialized in your code
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)