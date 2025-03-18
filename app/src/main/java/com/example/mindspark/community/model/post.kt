package com.example.mindspark.community.model

//data class Post(
//    val postId: String = "",
//    val userId: String = "",
//    val userName: String = "",
//    val content: String = "",
//    val timestamp: Long = System.currentTimeMillis(),
//    val likes: Long = 0,  // Change from Int to Long
//    val likedBy: List<String> = emptyList()  // Ensure this is initialized
//)

data class Post(
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val commentId: String = "",
    val profileImageUrl: String = "",
    val content: String = "",
    val imageUrl: String = "", // Ensure this field exists
    val timestamp: Long = 0,
    val likes: Int = 0,
    val likedBy: List<String> = listOf()
)
