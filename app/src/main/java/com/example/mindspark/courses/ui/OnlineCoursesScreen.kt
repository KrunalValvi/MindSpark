package com.example.mindspark.courses.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.CustomTextField_Image
import com.example.mindspark.courses.components.ToggleSelectionRowCourses
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.home.components.SectionHeader
import com.example.mindspark.home.components.TopMentorsListVertical
import com.example.mindspark.myCourses.components.MyCompletedCourseHorizontal

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CoursesListScreen(
    navController: NavController,
    startWithMentors: Boolean = false // New parameter
) {
    var search by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf(if (startWithMentors) "Mentors" else "Courses") }

    // State for courses loaded from Firebase
    var courses by remember { mutableStateOf(listOf<com.example.mindspark.courses.model.CourseModel>()) }
    var filteredCourses by remember { mutableStateOf(listOf<com.example.mindspark.courses.model.CourseModel>()) }
    var mentors by remember { mutableStateOf(listOf<MentorModel>()) }
    var filteredMentors by remember { mutableStateOf(listOf<MentorModel>()) }

    // Load courses and mentors asynchronously
    LaunchedEffect(Unit) {
        courses = CourseData.getPopularCourses()
        filteredCourses = courses
        mentors = MentorData.getAllMentors()
        filteredMentors = mentors
    }

    // Filter function with improved search and logging
    LaunchedEffect(search, selectedOption, courses, mentors) {
        Log.d("OnlineCoursesScreen", "Search triggered - Query: '$search', Option: $selectedOption")
        Log.d("OnlineCoursesScreen", "Total mentors before filter: ${mentors.size}")
        
        if (search.isBlank()) {
            filteredCourses = courses
            filteredMentors = mentors
            Log.d("OnlineCoursesScreen", "Blank search - showing all items")
        } else {
            val searchLower = search.lowercase().trim()
            when (selectedOption) {
                "Courses" -> {
                    filteredCourses = courses.filter { course ->
                        course.title.lowercase().contains(searchLower) ||
                        course.category.lowercase().contains(searchLower)
                    }
                    Log.d("OnlineCoursesScreen", "Filtered courses: ${filteredCourses.size} of ${courses.size}")
                }
                "Mentors" -> {
                    filteredMentors = mentors.filter { mentor ->
                        val nameMatch = mentor.name.lowercase().contains(searchLower)
                        val professionMatch = mentor.profession.lowercase().contains(searchLower)
                        Log.d("OnlineCoursesScreen", "Checking mentor: ${mentor.name} - nameMatch: $nameMatch, professionMatch: $professionMatch")
                        nameMatch || professionMatch
                    }
                    Log.d("OnlineCoursesScreen", "Filtered mentors: ${filteredMentors.size} of ${mentors.size}")
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = if (selectedOption == "Mentors") "Mentors" else "Online Courses",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(start = 16.dp, end = 16.dp, top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomTextField_Image(
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
                    )
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .offset(x = (-6).dp)
                            .clip(RoundedCornerShape(20))
                            .clickable { navController.navigate("FilterScreen") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { navController.navigate("CoursesFilterScreen") }
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ToggleSelectionRowCourses(
                options = listOf("Courses", "Mentors"),
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            when (selectedOption) {
                "Courses" -> {
                    // Courses Section
                    SectionHeader(
                        title = if (search.isBlank()) "All Courses" else "Results for \"$search\"",
                        onSeeAllClick = { navController.navigate("PopularCoursesList") }
                    )

                    // Courses List (filtered)
                    MyCompletedCourseHorizontal(
                        navController = navController,
                        courses = filteredCourses,
                        onCourseClick = { course ->
                            navController.navigate("CourseDetailScreen/${course.docId}")
                        }
                    )
                }
                "Mentors" -> {
                    // Mentors section
                    SectionHeader(
                        title = if (search.isBlank()) "Available Mentors" else "Results for \"$search\"",
                        onSeeAllClick = { navController.navigate("TopMentorScreen") }
                    )

                    TopMentorsListVertical(
                        mentors = filteredMentors,
                        onMentorClick = { mentor ->
                            Log.d("OnlineCoursesScreen", "Mentor clicked: ${mentor.name}, ID: ${mentor.id}, UserId: ${mentor.userId}")
                            // Pass the userId (Firebase document ID) instead of the numeric id
                            navController.navigate("SingleMentorDetails/${mentor.userId}")
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoursesListScreenPreview() {
    CoursesListScreen(navController = NavController(LocalContext.current))
}
