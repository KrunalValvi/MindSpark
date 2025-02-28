package com.example.mindspark.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.CustomTextField
import com.example.mindspark.courses.data.CardData
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.home.components.CategoriesListShow
import com.example.mindspark.home.components.HomeHeader
import com.example.mindspark.home.components.PopularCoursesListVertical
import com.example.mindspark.home.components.SectionHeader
import com.example.mindspark.home.components.SpecialOfferCard
import com.example.mindspark.home.components.TopMentorsListHorizontal

@Composable
fun HomeScreen(navController: NavController) {
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val cards = CardData.getCardDetails()
    val allCourses = CourseData.getPopularCourses()
    val filteredCourses by remember(selectedCategory) {
        derivedStateOf {
            if (selectedCategory == "All") {
                allCourses
            } else {
                allCourses.filter { it.category == selectedCategory }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color(0xFFF5F9FF))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 3.dp,
                    end = 3.dp,
                    top = 3.dp,
                )
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
                            .clickable { navController.navigate("SearchScreen") }
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
                    navController.navigate("PopularCoursesList") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Categories List
            CategoriesListShow(
                categories = CourseData.getAllCategories(),
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onAllSelected = {
                    selectedCategory = "All"
                }
            )

            // Popular Courses List with filtered courses
            if (filteredCourses.isEmpty()) {
                Text(
                    text = "Course not available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                PopularCoursesListVertical(
                    courses = filteredCourses,
                    onCourseClick = { course ->
                        navController.navigate("CourseDetailScreen/${course.id}")
                    }
                )
            }

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
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(context = LocalContext.current))
}