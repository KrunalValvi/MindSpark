package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.FeatureModel
import com.example.mindspark.home.components.PopularCoursesListVertical
import com.example.mindspark.ui.theme.customTypography


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
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
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        )
    )
}


@Composable
fun CourseDetailComponents(course: CourseModel) {
    var selectedTab by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val words = course.about.split(" ")
    val displayText = if (expanded || words.size <= 50) {
        course.about
    } else {
        words.take(50).joinToString(" ") + "..."
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),

            ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                text = "But how much. or rather, can it now do as much\n" +
                        "as it did then? Nor am I unaware that there is\n" +
                        "utility in history. not only pleasure.",
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFFF5F9FF)
            ) {
                listOf("About", "Curriculum").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.customTypography.jost.semiBold
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (selectedTab) {
                0 -> CourseList()
                1 -> Reviews()
            }
        }
    }

}

@Composable
fun CourseList() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        PopularCoursesListVertical(
            courses = CourseData.getPopularCourses(),
            onCourseClick = { }
        )
    }

}

@Composable
fun Reviews() {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(Modifier.height(8.dp))
        listOf(
            Review(
                "Will",
                "This course has been very useful. Mentor was well spoken totally loved it.",
                "578",
                "2 Weeks Ago"
            ),
            Review(
                "Martha E. Thompson",
                "This course has been very useful. Mentor was well spoken totally loved it. It had fun sessions as well.",
                "492",
                "3 Weeks Ago"
            )
        ).forEach { review ->
            ReviewItems(review)
        }
    }
}

@Composable
private fun ReviewItems(review: Review) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile_placeholder),
            contentDescription = "Reviewer",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = review.name,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.review,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Likes",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    review.likes,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp
                )
                Spacer(Modifier.width(20.dp))
                Text(
                    review.timeAgo,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}