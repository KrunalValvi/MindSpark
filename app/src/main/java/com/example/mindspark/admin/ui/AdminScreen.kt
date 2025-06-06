package com.example.mindspark.admin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.admin.ui.course.AdminCourseDetailScreen
import com.example.mindspark.admin.ui.course.AdminCoursesListScreen
import com.example.mindspark.admin.ui.mentor.MentorDetailScreen
import com.example.mindspark.admin.ui.mentor.MentorsListScreen
import com.example.mindspark.admin.ui.users.UserDetailScreen
import com.example.mindspark.admin.ui.users.UserListScreen
import com.example.mindspark.admin.ui.youtube.YoutubeDetail

sealed class Screen {
    object Main : Screen()

    object CoursesList : Screen()
    data class CourseDetail(val courseId: String?) : Screen() // null means new course

    object MentorsList : Screen()
    data class MentorDetail(val mentorId: String?) : Screen() // null means new mentor

    object UserList : Screen()
    data class UserDetail(val userId: String?) : Screen() // null means new user

    // Only one YouTube screen available
    object YoutubeDetail : Screen()
}

@Composable
fun AdminScreen(navController: NavController) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }

    when (val screen = currentScreen) {
        is Screen.Main -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { currentScreen = Screen.UserList },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Users")
                }
                Button(
                    onClick = { currentScreen = Screen.CoursesList },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Courses")
                }
                Button(
                    onClick = { currentScreen = Screen.MentorsList },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Mentors")
                }
                Button(
                    onClick = { currentScreen = Screen.YoutubeDetail },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Youtube")
                }
            }
        }
        is Screen.CoursesList -> {
            AdminCoursesListScreen(
                onBack = { currentScreen = Screen.Main },
                onCourseClick = { courseId ->
                    currentScreen = Screen.CourseDetail(courseId)
                }
            )
        }
        is Screen.CourseDetail -> {
            AdminCourseDetailScreen(
                courseId = screen.courseId,
                onBack = { currentScreen = Screen.CoursesList }
            )
        }
        is Screen.MentorsList -> {
            MentorsListScreen(
                onBack = { currentScreen = Screen.Main },
                onMentorClick = { mentorId ->
                    currentScreen = Screen.MentorDetail(mentorId)
                }
            )
        }
        is Screen.MentorDetail -> {
            MentorDetailScreen(
                mentorId = screen.mentorId,
                onBack = { currentScreen = Screen.MentorsList }
            )
        }
        is Screen.UserList -> {
            UserListScreen(
                onBack = { currentScreen = Screen.Main },
                onUserClick = { userId ->
                    currentScreen = Screen.UserDetail(userId)
                }
            )
        }
        is Screen.UserDetail -> {
            UserDetailScreen(
                userId = screen.userId,
                onBack = { currentScreen = Screen.UserList }
            )
        }
        is Screen.YoutubeDetail -> {
            // Directly call your single YouTube screen here
            YoutubeDetail(apiKey = "AIzaSyBRsaVZjMmGbdmUpswzHYp1XeiuFnl1vOE", navController = navController)
        }
    }
}
