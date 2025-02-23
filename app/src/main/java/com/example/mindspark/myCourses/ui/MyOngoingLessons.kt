package com.example.mindspark.myCourses.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.createGraph
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.firebase.updateUserFingerprint
import com.example.mindspark.myCourses.components.SectionCard
import com.example.mindspark.myCourses.data.getSampleSections
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun MyOngoingLessons(navController: NavController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

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
                    SectionCard(
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
