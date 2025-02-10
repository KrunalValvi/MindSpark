package com.example.mindspark.data.bookmark.data

import com.example.mindspark.data.bookmark.model.BookMarkModel

/**
 * BookMarkData.kt
 *
 * Handles data operations for bookmarks, such as fetching, adding, and removing entries.
 */
class BookMarkData {
    private val bookmarks = mutableListOf<BookMarkModel>()

    fun getBookmarks(): List<BookMarkModel> = bookmarks

    fun addBookmark(bookmark: BookMarkModel) {
        bookmarks.add(bookmark)
    }

    fun removeBookmark(id: String) {
        bookmarks.removeAll { it.id == id }
    }
}
