package com.example.mindspark.courses.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.CourseDetailComponents
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CourseDetailScreen(navController: NavController, id: Int) {
    val course = CourseData.getCourseById(id)
    val mentors = course?.mentorIds?.mapNotNull { MentorData.getMentorById(it) }
    val scrollState = rememberScrollState()
    val isAtBottom = remember { mutableStateOf(false) }

    // Track scroll position to show/hide the Apply button
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .collect { scrollPosition ->
                isAtBottom.value = scrollPosition >= scrollState.maxValue - 100
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    .verticalScroll(scrollState)
            ) {
                if (course != null && !mentors.isNullOrEmpty()) {
                    CourseDetailComponents(course, mentors) {
                        navController.navigate("SingleMentorDetails/${it.id}")
                    }
                    // Add extra padding at the bottom for the Apply button
                    Spacer(modifier = Modifier.height(80.dp))
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Course not found",
                            style = MaterialTheme.customTypography.mulish.bold
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isAtBottom.value,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(LightBlueBackground)
                .padding(16.dp),
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            AuthButton(
                text = "Purchase Course",
                onClick = {
                    navController.navigate("PaymentScreen") {
                        popUpTo("PaymentScreen") { inclusive = true }
                    }
                },
                modifier = Modifier.width(200.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    CourseDetailScreen(navController = NavController(LocalContext.current), id = 1)
}