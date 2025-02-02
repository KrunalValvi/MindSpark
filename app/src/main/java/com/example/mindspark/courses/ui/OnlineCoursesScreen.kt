package com.example.mindspark.courses.ui

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
import com.example.mindspark.home.components.PopularCoursesListVertical
import com.example.mindspark.home.components.SectionHeader
import com.example.mindspark.home.components.TopMentorsListVertical

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CoursesListScreen(navController: NavController) {

    var search by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Courses") }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Online Courses",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
//                .padding(padding)
                .padding(top = 40.dp)
                .padding(16.dp),
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
                            .size(38.dp) // Size of the box for the image
                            .offset(x = (-6).dp) // Shift the image a little to the left
                            .clip(RoundedCornerShape(20)) // Round corners for the image
                            .clickable { navController.navigate("FilterScreen") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { navController.navigate("CoursesFilterScreen") } // Ensure the image fills the box
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ToggleSelectionRowCourses(
                options = listOf("Courses", "Mentors"), // Pass the two options
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }, // Update selection on click
            )

            when (selectedOption) {
                "Courses" -> {

                    // Popular Courses Section
                    SectionHeader(
                        title = "Result for \"Graphic Design\"",
                        onSeeAllClick = { navController.navigate("PopularCoursesList") }
                    )

                    // Popular Courses List
                    PopularCoursesListVertical(
                        courses = CourseData.getPopularCourses(),
                        onCourseClick = { course ->
                            // Pass course.id to the CourseDetailScreen
                            navController.navigate("CourseDetailScreen/${course.mentorId}")
                        }
                    )
                }

                "Mentors" -> {

                    // Popular Courses Section
                    SectionHeader(
                        title = "Result for \"3D Design\"",
                        onSeeAllClick = { navController.navigate("TopMentorScreen") }
                    )

                    // Mentors List
                    TopMentorsListVertical(
                        mentors = MentorData.getTopMentors(),
                        onMentorClick = { mentor ->
                            navController.navigate("SingleMentorDetails/${mentor.id}")
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
