package com.example.mindspark.courses.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.FollowButton
import com.example.mindspark.courses.components.MentorCourseItem
import com.example.mindspark.courses.components.ReviewItem
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun SingleMentorDetails(navController: NavController, mentorId: Int) {
    val mentor = remember { MentorData.getMentorById(mentorId) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Mentor",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            item {
                mentor?.let {
                    MentorInfoSection(mentor)
                } ?: run {
                    Text("Mentor not found", modifier = Modifier.padding(16.dp), color = Color.Red)
                }
            }
            item {
                mentor?.let {
                    CoursesSection(navController, mentor) // Pass navController here
                    ReviewsSection(mentor)
                }
            }
        }
    }
}

@Composable
private fun MentorInfoSection(mentor: MentorModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Image Placeholder
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mentor Name
        Text(
            text = mentor.name,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 21.sp,
        )

        // Mentor Profession
        Text(
            text = mentor.profession,
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 13.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = mentor.courses.toString(),
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 17.sp
                )
                Text(
                    text = "Courses",
                    fontSize = 13.sp,
                    style = MaterialTheme.customTypography.mulish.bold
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = mentor.students.toString(),
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 17.sp
                )
                Text(
                    text = "Students",
                    fontSize = 13.sp,
                    style = MaterialTheme.customTypography.mulish.bold
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = mentor.ratings.toString(),
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 17.sp
                )
                Text(
                    text = "Ratings",
                    fontSize = 13.sp,
                    style = MaterialTheme.customTypography.mulish.bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Buttons Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val buttonModifier = Modifier
                .height(60.dp)
                .weight(1f)
                .padding(8.dp)

            FollowButton(modifier = buttonModifier)

            Button(
                onClick = { /* Message Click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5)),
                modifier = buttonModifier,
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
private fun CoursesSection(navController: NavController, mentor: MentorModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Courses", style = MaterialTheme.customTypography.jost.semiBold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        mentor.coursesList.forEach { course ->
            MentorCourseItem(course = course) {
                navController.navigate("CourseDetailScreen/${course.title}") // Navigate to CourseDetailScreen with course title
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ReviewsSection(mentor: MentorModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Reviews", style = MaterialTheme.customTypography.jost.semiBold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        mentor.reviews.forEach { review ->
            ReviewItem(review)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleMentorDetailsPreview() {
    SingleMentorDetails(navController = NavController(LocalContext.current), mentorId = 1)
}