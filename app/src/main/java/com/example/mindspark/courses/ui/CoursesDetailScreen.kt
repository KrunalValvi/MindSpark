@file:Suppress("DEPRECATION")
package com.example.mindspark.courses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.CourseVideoSection
import com.example.mindspark.courses.components.FeaturesSection
import com.example.mindspark.courses.components.InstructorSection
import com.example.mindspark.courses.components.MainContentCard
import com.example.mindspark.courses.components.ReviewsSection
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.home.components.SectionHeader
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CourseDetailScreen(navController: NavController) {
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
            CourseVideoSection()
            MainContentCard(selectedTab = selectedTab, onTabSelected = { selectedTab = it },course = CourseModel(
                category = "Graphic Design",
                title = "Graphic Design Advanced",
                price = "850/-",
                rating = "4.2",
                students = "7830 Std",
                videos = "21",
                hours = "42",
                difficultyLevel = "Intermediate",
                language = "English",
                certification = "Yes",
                about = "Graphic design is now a popular profession, offering vast career opportunities. " +
                        "This course will help you master advanced graphic design skills, from typography to branding."
            )
            )
            InstructorSection(mentor = MentorModel(
                name = "John Doe",
                profession = "Graphic Designer",
                id = 1
            ))
            FeaturesSection()
            ReviewsSection()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    CourseDetailScreen(navController = NavController(LocalContext.current))
}

