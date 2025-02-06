package com.example.mindspark.bookmark.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.bookmark.model.BookMarkModel
import com.example.mindspark.courses.model.CourseModel

@Composable
fun BookMarkList(bookmarks: List<BookMarkModel>, courses: List<CourseModel>, onCourseClick: (BookMarkModel) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(bookmarks.size) { index ->
            val course = courses.find { it.id == bookmarks[index].courseId }
            if (course != null) {
                BookMarkCard(course = course, bookmark = bookmarks[index], onCourseClick = onCourseClick)
            }
        }
    }
}