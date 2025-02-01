package com.example.mindspark.courses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.ui.login.LoginScreen
import com.example.mindspark.courses.components.*
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.courses.model.CourseModel

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CourseDetailScreen(
    navController: NavController,
    id: Int,
) {
    val course = CourseData.getPopularCourses().find { it.id == id }
    val mentor = MentorData.getTopMentors().find { it.id == course?.id }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Course Details",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            if (course != null && mentor != null) {
                CourseVideoSection()
                MainContentCard(course)
                InstructorSection(mentor)
                FeaturesSection(course)
                ReviewsSection()
            } else {
                Text("Course or Mentor not found")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    CourseDetailScreen(navController = NavController(LocalContext.current), id = 1)
}