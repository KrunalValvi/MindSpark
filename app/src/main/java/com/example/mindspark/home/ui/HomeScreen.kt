package com.example.mindspark.home.ui

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.home.components.CategoriesListShow
import com.example.mindspark.home.components.HomeHeader
import com.example.mindspark.home.components.PopularCoursesListVertical
import com.example.mindspark.home.components.SectionHeader
import com.example.mindspark.home.components.SpecialOfferCard
import com.example.mindspark.home.components.TopMentorsListHorizontal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun HomeScreen(navController: NavController) {
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val cards = CardData.getCardDetails()

    // State for courses loaded from Firebase
    var allCourses by remember { mutableStateOf(listOf<CourseModel>()) }
    var categoryStrings by remember { mutableStateOf(listOf("All")) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            // Use withContext to handle potential long-running operation
            val loadedCourses = withContext(Dispatchers.IO) {
                CourseData.getPopularCourses()
            }

            // Log detailed course information
            Log.d("HomeScreen", "Loaded courses: ${loadedCourses.size}")
            loadedCourses.forEach { course ->
                Log.d("HomeScreen", "Course Details: " +
                        "ID: ${course.id}, " +
                        "Title: ${course.title}, " +
                        "Category: ${course.category}, " +
                        "Price: ${course.price}"
                )
            }

            // Update states
            allCourses = loadedCourses
            categoryStrings = (listOf("All") + loadedCourses.map { it.category }).distinct()
//            categoryStrings = listOf("All") + loadedCourses.map { it.category }.filter { it != "All" }.distinct()

            // Log the updated categories
            // Update loading state
            isLoading = false
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error loading courses", e)
            errorMessage = "Failed to load courses: ${e.localizedMessage}"
            isLoading = false
        }
    }

    // Convert category strings to a list of CourseCategory
    val categories: List<CourseCategory> by remember(categoryStrings) {
        derivedStateOf {
            categoryStrings.map { str ->
                CourseCategory.values().find { it.value == str } ?: CourseCategory.All
            }
        }
    }

    // Filter courses based on selected category
    val filteredCourses by remember(selectedCategory, allCourses) {
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
            .background(LightBlueBackground)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 3.dp, end = 3.dp, top = 3.dp)
        ) {
            // Header Section
            HomeHeader(navController = navController)

            // Search Section
            CustomTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = "Search",
                leadingIcon = {
                    androidx.compose.material3.Icon(
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

            // Categories List Show using computed CourseCategory list
            CategoriesListShow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onAllSelected = { selectedCategory = "All" }
            )

            // Loading and Error Handling
            when {
                isLoading -> {
                    Text(
                        text = "Loading courses...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "Unknown error occurred",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                allCourses.isEmpty() -> {
                    Text(
                        text = "No courses available. Please check your connection.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    // Popular Courses List with filtered courses
                    PopularCoursesListVertical(
                        courses = filteredCourses,
                        onCourseClick = { course ->
                            navController.navigate("CourseDetailScreen/${course.docId}")
                        }
                    )
                }
            }

            // Top Mentors Section
            SectionHeader(
                title = "Top Mentor",
                onSeeAllClick = { navController.navigate("TopMentorScreen") }
            )

            // Mentors List
            TopMentorsListHorizontal(
                mentors = emptyList(), // Pass empty list as fallback, Firebase data will be fetched inside the component
                onMentorClick = { mentor ->
                    // Use userId instead of id for more reliable navigation if needed
                    navController.navigate("SingleMentorDetails/${mentor.userId}")
                }
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
        }

        // Add the Floating Chat Button
//        FloatingChatButton()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}