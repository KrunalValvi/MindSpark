package com.example.mindspark.data.home.data

import com.example.mindspark.data.home.model.SearchModel

/**
 * SearchData.kt
 *
 * Handles data operations for search functionality.
 */
class SearchData {
    private val searchResults = listOf(
        SearchModel("Android Course", "Learn Android Development"),
        SearchModel("UI Design", "Master UI/UX")
    )

    fun search(query: String): List<SearchModel> {
        return searchResults.filter { it.title.contains(query, ignoreCase = true) }
    }
}
