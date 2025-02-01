package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CourseVideoSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.PlayCircle,
            contentDescription = "Play Video",
            modifier = Modifier.size(50.dp),
            tint = Color.White
        )
    }
}

@Composable
fun MainContentCard(course: CourseModel) {
    var selectedTab by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CourseHeader(course)
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
                0 -> AboutSection(course)
                1 -> CurriculumContent()
            }
        }
    }
}

@Composable
fun AboutSection(course: CourseModel) {
    var expanded by remember { mutableStateOf(false) }
    val words = course.about.split(" ")
    val displayText = if (expanded || words.size <= 50) {
        course.about
    } else {
        words.take(50).joinToString(" ") + "..."
    }

    Column {
        Text(
            text = displayText,
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 13.sp
        )
        if (words.size > 50) {
            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (expanded) "Read Less" else "Read More",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.customTypography.mulish.bold
                )
            }
        }
    }
}

@Composable
fun CurriculumContent() {
    Column {
        // Section 1
        CurriculumSection(
            title = "Section 01 - Introduction",
            duration = "25 Mins",
            lessons = listOf(
                "Why Using Graphic Design" to "15 Mins",
                "Setup Your Graphic Design" to "10 Mins"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2
        CurriculumSection(
            title = "Section 02 - Getting Started",
            duration = "45 Mins",
            lessons = listOf(
                "Basic Design Principles" to "20 Mins",
                "Color Theory Fundamentals" to "25 Mins"
            )
        )
    }
}

@Composable
private fun CurriculumSection(
    title: String,
    duration: String,
    lessons: List<Pair<String, String>>
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 15.sp
            )
            Text(
                text = duration,
                style = MaterialTheme.customTypography.mulish.extraBold,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        lessons.forEachIndexed { index, (title, duration) ->
            LessonItem(
                index = index + 1,
                title = title,
                duration = duration
            )
        }
    }
}

@Composable
private fun LessonItem(
    index: Int,
    title: String,
    duration: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF5F9FF), RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = index.toString().padStart(2, '0'),
            style = MaterialTheme.customTypography.jost.semiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp
            )
            Text(
                text = duration,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp
            )
        }
        Icon(
            imageVector = Icons.Default.PlayCircle,
            contentDescription = "Play",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun CourseHeader(course: CourseModel) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.category,
                style = MaterialTheme.customTypography.mulish.bold,
                color = Color(0xFFFF6B00),
                fontSize = 12.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(12.dp),
                    tint = Color(0xFFFF9C07)
                )
                Text(
                    course.rating,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = course.title,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(Icons.Default.Videocam, null, Modifier.size(17.dp))

                Text(
                    " ${course.videos} Classes ",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 11.sp
                )

                Spacer(Modifier.width(8.dp))

                Text("|")

                Spacer(Modifier.width(8.dp))

                Icon(Icons.Default.AccessTime, null, Modifier.size(17.dp))

                Text(
                    " ${course.hours} Hours ",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 11.sp
                )
            }
            Text(
                text = course.price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Composable
fun InstructorSection(mentor: MentorModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Instructor",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Instructor",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = mentor.name,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 17.sp
                )
                Text(
                    text = mentor.profession,
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun FeaturesSection(course: CourseModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "What You'll Get",
            style = MaterialTheme.customTypography.jost.semiBold, // Update to your typography style
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))

        course.features.forEach { feature ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = feature.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = feature.description,
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ReviewsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Reviews",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))

        ReviewItem(
            name = "Will",
            review = "This course has been very useful. Mentor was well spoken totally loved it.",
            likes = "578",
            timeAgo = "2 Weeks Ago"
        )
        ReviewItem(
            name = "Martha E. Thompson",
            review = "This course has been very useful. Mentor was well spoken totally loved it. It had fun sessions as well.",
            likes = "492",
            timeAgo = "3 Weeks Ago"
        )
    }
}

@Composable
private fun ReviewItem(
    name: String,
    review: String,
    likes: String,
    timeAgo: String
) {
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
                text = name,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review,
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
                    likes,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp
                )
                Spacer(Modifier.width(20.dp))
                Text(
                    timeAgo,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}