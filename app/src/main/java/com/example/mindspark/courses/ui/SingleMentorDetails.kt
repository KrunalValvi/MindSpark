package com.example.mindspark.courses.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.CourseDetailComponents
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun SingleMentorDetails(navController: NavController) {
    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Mentor",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            MentorInfoSection()
            CourseDetailComponents(
                course = CourseModel(
                    category = "Graphic Design",
                    title = "Photoshop for Beginners",
                    price = "1200/-",
                    rating = "4.5",
                    students = "3800 Std",
                    videos = "20",
                    hours = "40",
                    difficultyLevel = "Beginner",
                    language = "English",
                    certification = "Yes",
                    about = "Learn the basics of Photoshop, including photo editing and graphic creation.",
                    mentorId = 3,
                    features = listOf()
                )
            )
        }
    }
}

@Composable
private fun MentorInfoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Black) // Placeholder for image
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Name
        Text(
            text = "Mary Jones",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 21.sp,
        )

        // Designation
        Text(
            text = "Graphic Designer At Google",
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 13.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatItem("26", "Courses")
            StatItem("15800", "Students")
            StatItem("8750", "Ratings")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Buttons Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { /* Follow Click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F1FF)),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Follow",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = { /* Message Click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5)),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Message",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.customTypography.jost.semiBold, fontSize = 17.sp)
        Text(text = label, fontSize = 13.sp, style = MaterialTheme.customTypography.mulish.bold)
    }
}

@Preview(showBackground = true)
@Composable
fun SingleMentorDetailsPreview() {
    SingleMentorDetails(navController = NavController(LocalContext.current))
}