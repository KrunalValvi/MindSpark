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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.home.components.PopularCoursesListHorizontal
import com.example.mindspark.home.components.CategoriesList

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun PopularCoursesList(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All") }
    var allCourses by remember { mutableStateOf(listOf<CourseModel>()) }
    var categoryStrings by remember { mutableStateOf(listOf("All")) }

    // Load courses and categories asynchronously
    LaunchedEffect(Unit) {
        allCourses = CourseData.getPopularCourses()
        // Compute distinct categories from loaded courses and prepend "All"
        categoryStrings = listOf("All") + allCourses.map { it.category }.distinct()
    }

    // Convert list of String to list of CourseCategory using enum values.
    // (Ensure that your CourseCategory enum includes a value "All" and that each category's 'value' property matches the strings.)
    val categories: List<CourseCategory> = categoryStrings.map { categoryName ->
        CourseCategory.values().find { it.value == categoryName } ?: CourseCategory.All
    }

    val filteredCourses by remember(selectedCategory, allCourses) {
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
                .padding(start = 4.dp, end = 4.dp, top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Categories List using computed categories
            CategoriesList(
                categories = categories,
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
                PopularCoursesListHorizontal(
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
