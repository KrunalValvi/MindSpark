package com.example.mindspark.myCourses.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.courses.model.CourseModel

@Composable
fun MyCompletedCourseHorizontal(
    navController: NavController, // Accept NavController
    courses: List<CourseModel>,
    onCourseClick: (CourseModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(courses.size) { index ->
            val course = courses[index]
            MyCompletedCourseCardHorizontal(course, onCourseClick) // Pass NavController
        }
    }
}


@Composable
fun MyOngoingCourseHorizontal(courses: List<CourseModel>, onCourseClick: (CourseModel) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Add spacing between items
    ) {
        items(courses.size) { index ->
            val course = courses[index]
            MyOngoingCourseCardHorizontal(course, onCourseClick)
        }
    }
}
