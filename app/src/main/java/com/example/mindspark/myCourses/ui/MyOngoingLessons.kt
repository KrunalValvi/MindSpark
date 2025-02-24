package com.example.mindspark.myCourses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.createGraph
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.myCourses.components.OngoingSectionCard
import com.example.mindspark.myCourses.data.getSampleSections

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun MyOngoingLessons(navController: NavController) {

    // Example data from our data file
    val sectionsList = remember { getSampleSections() }

    Scaffold(
        topBar = {
            AuthTopBar(
                title = "My Ongoing Courses",
                onBackClick = { navController.navigateUp() }
            )
        },
        containerColor = LightBlueBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Give LazyColumn a weight so it doesn't occupy all space
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // <-- This makes the LazyColumn take the remaining space
                    .background(LightBlueBackground)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(sectionsList) { index, section ->
                    OngoingSectionCard(
                        section = section,
                        sectionIndex = index + 1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AuthButton(
                text = "Continue Courses",
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )


        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyOngoingLessonsPreview() {
    MyOngoingLessons(
        navController = NavController(LocalContext.current).apply {
            createGraph(startDestination = "MyLessons") {}
        }
    )
}
