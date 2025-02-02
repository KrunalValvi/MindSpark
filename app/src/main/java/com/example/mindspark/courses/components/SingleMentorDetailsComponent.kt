package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
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
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CourseDetailComponents(course: CourseModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("About", "Curriculum")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "But how much. or rather, can it now do as much\n" +
                            "as it did then? Nor am I unaware that there is\n" +
                            "utility in history. not only pleasure.",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFFF5F9FF)
                ) {
                    tabs.forEachIndexed { index, title ->
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
}

@Composable
fun CourseList() {

    CourseData.getPopularCourses().forEach { course ->
        CourseItem(course)
        Spacer(modifier = Modifier.height(10.dp))
    }

}

@Composable
fun FollowButton(modifier: Modifier = Modifier) {
    var isFollowing by remember { mutableStateOf(false) }

    Button(
        onClick = { isFollowing = !isFollowing },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE8F1FF)
        ),
        modifier = modifier,
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(
            text = if (isFollowing) "Unfollow" else "Follow",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CourseItem(course: CourseModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = course.title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFF9C07)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.rating,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = course.price,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun Reviews() {
    Column(modifier = Modifier.padding(16.dp)) {
        listOf(
            Review("Will", "This course has been very useful. Mentor was well spoken totally loved it.", "578", "2 Weeks Ago"),
            Review("Martha E. Thompson", "This course has been very useful. Mentor was well spoken totally loved it. It had fun sessions as well.", "492", "3 Weeks Ago")
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
                    imageVector = Icons.Default.Favorite,
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