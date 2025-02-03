package com.example.mindspark.home.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.CustomTextField
import com.example.mindspark.home.components.*
import com.example.mindspark.courses.data.CardData
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData

@Composable
fun HomeScreen(navController: NavController) {

    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val cards = CardData.getCardDetails()

    Box(
        modifier = Modifier
            .background(Color(0xFFF5F9FF))
            .padding(top = 10.dp)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            HomeHeader(navController = navController)

            // Search Section
            CustomTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = "Search",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 4.dp)
                            .clickable{ navController.navigate("SearchScreen")}
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            // Special Offer Card
            SpecialOfferCard(cards = cards)

            // Popular Courses Section
            SectionHeader(
                title = "Popular Courses",
                onSeeAllClick = {
                    navController.navigate("PopularCoursesList"){
                        popUpTo(navController.graph.startDestinationId) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            // Categories List
            CategoriesListShow(
                categories = CourseData.getAllCategories(),
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onAllSelected = {
                    navController.navigate("CoursesListScreen") // Navigate to the "All" page
                }
            )

            // Popular Courses List
            PopularCoursesListHorizontal(
                courses = CourseData.getPopularCourses(),
                onCourseClick = { course ->
                    // Pass course.id to the CourseDetailScreen
                    navController.navigate("CourseDetailScreen/${course.id}")
                }
            )

            // Top Mentors Section
            SectionHeader(
                title = "Top Mentor",
                onSeeAllClick = { navController.navigate("TopMentorScreen") }
            )

            // Mentors List
            TopMentorsListHorizontal(
                mentors = MentorData.getTopMentors(),
                onMentorClick = { mentor ->
                    // Navigate to SingleMentorDetails with the mentor's ID
                    navController.navigate("SingleMentorDetails/${mentor.id}")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(context = LocalContext.current))
}