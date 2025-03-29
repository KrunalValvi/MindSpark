package com.example.mindspark.courses.ui

import android.net.Uri
import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.CourseDetailComponents
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CourseDetailScreen(navController: NavController, id: String) {
    var course by remember { mutableStateOf<CourseModel?>(null) }
    var mentors by remember { mutableStateOf<List<MentorModel>>(emptyList()) }
    val scrollState = rememberScrollState()
    val isAtBottom = remember { mutableStateOf(false) }

    // Fetch course data from Firebase using the document ID
    LaunchedEffect(id) {
        course = CourseData.getCourseByDocId(id)
        // Even if no mentors are found, we want to show course details.
        mentors = course?.mentorIds?.mapNotNull { MentorData.getMentorById(it) } ?: emptyList()
    }

    // Track scroll position to show/hide the Purchase button
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
                if (course != null) {
                    // Display course details. We pass a lambda for onMentorClick.
                    CourseDetailComponents(
                        course = course!!,
                        mentors = mentors,
                        onMentorClick = { mentor ->
                            navController.navigate("SingleMentorDetails/${mentor.id}")
                        },
                        onPlayVideo = { videoUrl ->
                            // Encode the video URL before passing it to the route.
                            val encodedUrl = Uri.encode(videoUrl)
                            navController.navigate("VideoPlayerScreen/$encodedUrl")
                        }
                    )
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
                modifier = Modifier
                    .width(200.dp)
                    .wrapContentHeight()
            )
        }
    }
}

/* Uncomment preview if needed
@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    CourseDetailScreen(navController = NavController(LocalContext.current), id = "sampleDocId")
}
*/
