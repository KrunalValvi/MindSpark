@file:Suppress("DEPRECATION")

package com.example.mindspark.courses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CourseDetailScreen(navController: NavController) {
    // State to control which tab is selected: 0 = About, 1 = Curriculum
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Courses Details",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Video section remains outside the card.
            CourseVideoSection()

            if (selectedTab == 0) {
                // About card with course info, tabs, and about description.
                AboutCard(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                // Additional sections below the About card.
                InstructorSection()
                FeaturesSection()
                ReviewsSection()
            } else {
                // Curriculum card now also includes CourseInfoContent and CourseTabs at the top.
                CurriculumCard(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
            }
        }
    }
}

// ───────────── VIDEO SECTION ─────────────
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

// ───────────── ABOUT CARD ─────────────
// This card wraps the CourseInfoContent, CourseTabs, and AboutSection.
@Composable
fun AboutCard(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Use CourseInfoContent to avoid nested cards.
            CourseInfoContent()
            Spacer(modifier = Modifier.height(16.dp))
            CourseTabs(selectedTab = selectedTab, onTabSelected = onTabSelected)
            Spacer(modifier = Modifier.height(16.dp))
            AboutSection()
        }
    }
}

// ───────────── CURRICULUM CARD ─────────────
// This card wraps CourseInfoContent, CourseTabs, and the CurriculumSection.
@Composable
fun CurriculumCard(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display the same header as in the About card.
            CourseInfoContent()
            Spacer(modifier = Modifier.height(16.dp))
            CourseTabs(selectedTab = selectedTab, onTabSelected = onTabSelected)
            Spacer(modifier = Modifier.height(16.dp))
            CurriculumSection()
        }
    }
}

// ───────────── COURSE INFO CONTENT ─────────────
// The inner content of the course information.
@Composable
fun CourseInfoContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Graphic Design",
                style = MaterialTheme.customTypography.mulish.bold,
                color = Color(0xFFFF6B00),
                fontSize = 12.sp
            )
            Row {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier
                        .size(12.dp),
                    tint = Color.Yellow
                )
                Text("4.2", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Design Principles: Organizing ..",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row {
                Icon(Icons.Default.Videocam, contentDescription = "Classes", Modifier.size(17.dp))
                Text(" 21 Classes ", style = MaterialTheme.customTypography.mulish.bold, fontSize = 11.sp)

                Spacer(Modifier.width(8.dp))

                Text("|")

                Spacer(Modifier.width(8.dp))

                Icon(Icons.Default.AccessTime, contentDescription = "Hours", Modifier.size(17.dp))
                Text(" 42 Hours ", style = MaterialTheme.customTypography.mulish.bold, fontSize = 11.sp)
            }
            Text(
                text = "499/-",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
            )
        }
    }
}

// ───────────── TABS (About & Curriculum) ─────────────
@Composable
fun CourseTabs(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("About", "Curriculum")
    Row(modifier = Modifier.fillMaxWidth()) {
        tabs.forEachIndexed { index, title ->
            TextButton(
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .weight(1f)
                    .background(if (index == selectedTab) Color(0xFFF5F9FF) else Color(0xFfE8F1FF))
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

// ───────────── ABOUT SECTION ─────────────
// This section now includes an inline "Read More" / "Read Less" toggle.
@Composable
fun AboutSection() {
    var expanded by remember { mutableStateOf(false) }
    val shortText = "Graphic Design is a growing field with many career opportunities. In this course, you will learn the fundamentals of design, color theory, and visual communication."
    val longText = shortText + " Additionally, you will explore advanced topics such as modern design trends, practical projects, expert tips, and career guidance to help you excel in the competitive world of graphic design."

    // Build an annotated string so that the toggle appears inline.
    val annotatedText: AnnotatedString = buildAnnotatedString {
        append(if (expanded) longText else shortText)
        append(" ") // Space before the toggle text.
        pushStringAnnotation(tag = "TOGGLE", annotation = if (expanded) "read_less" else "read_more")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
            append(if (expanded) "Read Less" else "Read More")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.customTypography.mulish.bold.copy
            (fontSize = 13.sp),
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

// ───────────── CURRICULUM SECTION ─────────────
@Composable
fun CurriculumSection() {
    Column {
        Text("Section 01 - Introduction", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("25 Mins", color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        LessonItem(1, "Why Using Graphic Design", "15 Mins")
        LessonItem(2, "Setup Your Graphic Design", "10 Mins")
    }
}

// ───────────── SINGLE LESSON ITEM ─────────────
@Composable
fun LessonItem(index: Int, title: String, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text(
            text = index.toString().padStart(2, '0'),
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(duration, color = Color.Gray)
        }
        Icon(Icons.Default.PlayCircle, contentDescription = "Play", tint = Color.Blue)
    }
}

// ───────────── INSTRUCTOR SECTION ─────────────
@Composable
fun InstructorSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Instructor", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                Text("Robert Jr", fontWeight = FontWeight.Bold)
                Text("Graphic Design", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

// ───────────── FEATURES SECTION (What You’ll Get) ─────────────
@Composable
fun FeaturesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("What You’ll Get", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = text)
                Spacer(Modifier.width(8.dp))
                Text(text)
            }
        }
    }
}

// ───────────── REVIEWS SECTION ─────────────
@Composable
fun ReviewsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Reviews", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        val reviews = listOf(
            "Will" to "This course has been very useful...",
            "Martha E. Thompson" to "It had fun sessions as well."
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
                    Text(name, fontWeight = FontWeight.Bold)
                    Text(review)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = "Likes", tint = Color.Red)
                        Spacer(Modifier.width(4.dp))
                        Text("578")
                        Spacer(Modifier.width(8.dp))
                        Text("2 Weeks Ago", color = Color.Gray)
                    }
                }
            }
        }
    }
}

// ───────────── ENROLL BUTTON (Fixed at Bottom) ─────────────
@Composable
fun EnrollButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /* TODO: Enroll action */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text("Enroll Course - 499/-", fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = "Enroll")
        }
    }
}

// ───────────── PREVIEW ─────────────
@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview1() {
    CourseDetailScreen(navController = NavController(LocalContext.current))
}

@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview2() {
    FeaturesSection()
}

@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview3() {
    ReviewsSection()
}

@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview4() {
    CurriculumCard(selectedTab = 0, onTabSelected = {})
}
