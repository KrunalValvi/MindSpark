package com.example.mindspark.bookmark.model

data class BookMarkModel(
    val title: String,
    val courseId: Int, // Changed from course to courseId
    val status: String
)