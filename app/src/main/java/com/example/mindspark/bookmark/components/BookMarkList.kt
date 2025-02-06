package com.example.mindspark.bookmark.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.bookmark.model.BookMarkModel

@Composable
fun BookMarkList(bookmarks: List<BookMarkModel>, onCourseClick: (BookMarkModel) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(bookmarks.size) { index ->
            BookMarkCard(bookmark = bookmarks[index], onCourseClick = onCourseClick )
        }
    }
}

