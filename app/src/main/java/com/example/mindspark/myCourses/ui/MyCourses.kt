package com.example.mindspark.myCourses.ui

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
import com.example.mindspark.myCourses.components.MyCompletedCourseHorizontal
import com.example.mindspark.myCourses.components.MyOngoingCourseHorizontal

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun MyCourseCompleted(
    navController: NavController,
    startWithMentors: Boolean = false // New parameter
) {

    var search by remember { mutableStateOf("") }
//    var selectedOption by remember { mutableStateOf(if (startWithMentors) "Completed" else "Ongoing") }
    var selectedOption by remember { mutableStateOf(if (startWithMentors) "Ongoing" else "Completed") }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "My Courses",
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
                placeholder = "Search for ...",
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
                            painter = painterResource(id = R.drawable.ic_search_bar),
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
                options = listOf("Completed", "Ongoing"),
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it },
            )

            when (selectedOption) {
                "Completed" -> {

                    // Popular Courses List
                    MyCompletedCourseHorizontal(
                        navController = navController, // Pass existing NavController
                        courses = CourseData.getPopularCourses(),
                        onCourseClick = { course ->
                            navController.navigate("MyLessons")
                        }
                    )

                }

                "Ongoing" -> {

                    // Popular Courses List
                    MyOngoingCourseHorizontal(
                        courses = CourseData.getPopularCourses(),
                        onCourseClick = { course ->
                            navController.navigate("MyOngoingLessons")
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
    MyCourseCompleted(navController = NavController(LocalContext.current))
}
