package com.example.mindspark.mentor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.admin.ui.course.AdminCoursesListScreen
import com.example.mindspark.auth.components.AuthTopBar

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun MentorScreen(navController: NavController ) {

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Upload Courses",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(start = 16.dp, end = 16.dp, top = 50.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {




        }
    }
}

@Preview(showBackground = true)
@Composable
fun MentorScreenPreview() {
    MentorScreen(navController = NavController(LocalContext.current))
}
