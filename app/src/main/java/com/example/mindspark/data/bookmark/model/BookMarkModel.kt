package com.example.mindspark.data.bookmark.model

/**
 * BookMarkModel.kt
 *
 * Model representing a bookmark entry.
 */
data class BookMarkModel(
    val id: String,
    val title: String,
    val url: String,      // URL or reference to the bookmarked content
    val timestamp: Long   // Unix timestamp for when the bookmark was created
)
