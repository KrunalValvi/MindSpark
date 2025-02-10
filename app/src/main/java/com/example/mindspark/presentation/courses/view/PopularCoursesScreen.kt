package com.example.mindspark.courses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.home.components.CategoriesList
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.home.components.PopularCoursesListHorizontal
import com.example.mindspark.home.components.PopularCoursesListVertical

private val LightBlueBackground = Color(0xFFF5F9FF)


@Composable
fun PopularCoursesList(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All") }
    val allCourses = CourseData.getPopularCourses()
    val filteredCourses by remember(selectedCategory) {
        derivedStateOf {
            if (selectedCategory == "All") {
                allCourses
            } else {
                allCourses.filter { it.category == selectedCategory }
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Popular Courses",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Categories List
            CategoriesList(
                categories = CourseData.getAllCategories(),
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Popular Courses List with filtered courses
            if (filteredCourses.isEmpty()) {
                Text(
                    text = "Course not available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                PopularCoursesListVertical(
                    courses = filteredCourses,
                    onCourseClick = { course ->
                        // Pass course.id to the CourseDetailScreen
                        navController.navigate("CourseDetailScreen/${course.id}")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopularCoursesListPreview() {
    PopularCoursesList(navController = NavController(LocalContext.current))
}