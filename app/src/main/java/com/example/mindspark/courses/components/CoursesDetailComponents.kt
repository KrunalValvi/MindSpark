package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.FeatureModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.myCourses.components.SectionCard
import com.example.mindspark.myCourses.data.getSampleSections
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CourseDetailComponents(
    course: CourseModel,
    mentors: List<MentorModel>,
    onMentorClick: (MentorModel) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val words = course.about.split(" ")
    val displayText = if (expanded || words.size <= 50) {
        course.about
    } else {
        words.take(50).joinToString(" ") + "..."
    }

    Column {
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

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(3.dp)
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
                    0 -> {
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
                    1 -> CurriculumContent()
                }
            }
        }
        InstructorsSection(mentors, onMentorClick = onMentorClick)
        ReviewsSection()
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
private fun CurriculumContent() {
    val sectionsList = remember { getSampleSections() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(sectionsList) { index, section ->
                SectionCard(
                    section = section,
                    sectionIndex = index + 1
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun InstructorsSection(mentors: List<MentorModel>, onMentorClick: (MentorModel) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Instructors", style = MaterialTheme.customTypography.jost.semiBold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        mentors.forEach { mentor ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMentorClick(mentor) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = mentor.imageRes),
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
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun ReviewsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Reviews", style = MaterialTheme.customTypography.jost.semiBold, fontSize = 18.sp)
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
            ReviewItem(review)
        }
    }
}

data class Review(val name: String, val review: String, val likes: String, val timeAgo: String)

@Composable
private fun ReviewItem(review: Review) {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(review.likes.toInt()) }

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
                fontSize = 17.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.review,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            isLiked = !isLiked
                            likeCount += if (isLiked) 1 else -1
                        }
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    likeCount.toString(),
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp
                )
                Spacer(Modifier.width(25.dp))
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
