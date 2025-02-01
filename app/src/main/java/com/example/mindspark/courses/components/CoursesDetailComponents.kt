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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Assignment
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.home.components.SectionHeader
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
fun MainContentCard(selectedTab: Int, onTabSelected: (Int) -> Unit, course: CourseModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CourseInfoContent(course)
            Spacer(modifier = Modifier.height(16.dp))
            CourseTabs(selectedTab = selectedTab, onTabSelected = onTabSelected)
            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> AboutSection(course)
                1 -> CurriculumContent()
            }
        }
    }
}

@Composable
fun CourseInfoContent(course: CourseModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.category,
                style = MaterialTheme.customTypography.mulish.bold,
                color = Color(0xFFFF6B00),
                fontSize = 12.sp
            )
            Row {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(12.dp),
                    tint = Color(0xFFFF9C07)
                )
                Text(course.rating, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = course.title,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Icon(Icons.Default.Videocam, contentDescription = "Classes", Modifier.size(17.dp))
                Text(" ${course.videos} Classes ", style = MaterialTheme.customTypography.mulish.bold, fontSize = 11.sp)

                Spacer(Modifier.width(8.dp))
                Text("|")
                Spacer(Modifier.width(8.dp))

                Icon(Icons.Default.AccessTime, contentDescription = "Hours", Modifier.size(17.dp))
                Text(" ${course.hours} Hours ", style = MaterialTheme.customTypography.mulish.bold, fontSize = 11.sp)
            }
            Text(
                text = course.price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
            )
        }
    }
}

@Composable
fun CourseTabs(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("About", "Curriculum")
    Row(modifier = Modifier.fillMaxWidth()) {
        tabs.forEachIndexed { index, title ->
            TextButton(
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .weight(1f)
                    .background(if (index == selectedTab) Color(0xFFE8F1FF) else Color(0xFFF5F9FF))
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@Composable
fun AboutSection(course: CourseModel) {
    val words = course.about.split(" ") // Split text into words

    // Only show "Read More" if the text has more than 50 words
    if (words.size <= 50) {
        Text(
            text = course.about,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp)
        )
    } else {
        var expanded by remember { mutableStateOf(false) }
        val displayText = if (expanded) course.about else words.take(50).joinToString(" ") + "..."

        val annotatedText: AnnotatedString = buildAnnotatedString {
            append(displayText)
            append(" ")
            pushStringAnnotation(tag = "TOGGLE", annotation = if (expanded) "read_less" else "read_more")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                append(if (expanded) " Read Less" else " Read More")
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
            maxLines = if (expanded) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "TOGGLE", start = offset, end = offset)
                    .firstOrNull()?.let {
                        expanded = !expanded
                    }
            }
        )
    }
}




@Composable
fun CurriculumContent() {
    Column {
        // Section 1
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Section 01 - Introduction",
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 15.sp
            )
            Text(
                "25 Mins",
                style = MaterialTheme.customTypography.mulish.extraBold,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LessonItem(1, "Why Using Graphic Design", "15 Mins")
        LessonItem(2, "Setup Your Graphic Design", "10 Mins")

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Section 02 - Getting Started",
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 15.sp
            )
            Text(
                "45 Mins",
                style = MaterialTheme.customTypography.mulish.regular,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LessonItem(3, "Basic Design Principles", "20 Mins")
        LessonItem(4, "Color Theory Fundamentals", "25 Mins")
    }
}
@Composable
fun LessonItem(index: Int, title: String, duration: String) {
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
            color = Color.Blue,
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
//                color = Color.Gray,
                fontSize = 13.sp
            )
        }
        Icon(
            Icons.Default.PlayCircle,
            contentDescription = "Play",
            tint = Color.Blue,
            modifier = Modifier.size(24.dp)
        )
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
fun FeaturesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "What You'll Get",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        val features = listOf(
            "25 Lessons" to Icons.Default.MenuBook,
            "Access Mobile, Desktop & TV" to Icons.Default.PhoneAndroid,
            "Beginner Level" to Icons.Default.BarChart,
            "Audio Book" to Icons.Default.Audiotrack,
            "Lifetime Access" to Icons.Default.AccessTime,
            "100 Quizzes" to Icons.Default.Assignment,
            "Certificate of Completion" to Icons.Default.Star
        )
        features.forEach { (text, icon) ->
            Row(
                modifier = Modifier.padding(vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = text,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = text,
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

        SectionHeader(
            title = "Reviews",
            onSeeAllClick = {}
        )
        Spacer(Modifier.height(8.dp))
        val reviews = listOf(
            "Will" to "This course has been very useful. Mentor was\n" +
                    "well spoken totally loved it.",
            "Martha E. Thompson" to "This course has been very useful. Mentor was\n" +
                    "well spoken totally loved it. It had fun sessions\n" +
                    "as well."
        )
        reviews.forEach { (name, review) ->
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
                            "578",
                            style = MaterialTheme.customTypography.mulish.extraBold,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "2 Weeks Ago",
                            style = MaterialTheme.customTypography.mulish.extraBold,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
