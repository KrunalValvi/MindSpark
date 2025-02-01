package com.example.mindspark.courses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Categories List
            CategoriesList(
                categories = CourseData.getAllCategories(),
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Popular Courses List
            PopularCoursesListVertical(
                courses = CourseData.getPopularCourses(),
                onCourseClick = { course ->
                    // Pass course.id to the CourseDetailScreen
                    navController.navigate("CourseDetailScreen")
                }
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopularCoursesListPreview() {
    PopularCoursesList(navController = NavController(LocalContext.current))

}