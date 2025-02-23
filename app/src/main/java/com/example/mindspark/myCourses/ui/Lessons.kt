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
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.firebase.updateUserFingerprint
import com.example.mindspark.myCourses.components.SectionCard
import com.example.mindspark.myCourses.data.getSampleSections
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun MyLessons(navController: NavController) {

    // Example data from our data file
    val sectionsList = remember { getSampleSections() }

    Scaffold(
        topBar = {
            AuthTopBar(
                title = "My Courses",
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

            // Bottom Row for Skip and Continue buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(Color(0xFFE8F1FF))
                        .clickable { navController.navigate("CertificateScreen") },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_course_certificate),
                        contentDescription = "Skip",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Continue Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(Color(0xFF1565C0))
                        .clickable {
                            navController.navigate("CertificateScreen")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 7.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "Start Course Again",
                            color = Color.White,
                            fontSize = 18.sp,
                            style = MaterialTheme.customTypography.jost.semiBold
                        )

                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Start Course Again",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyLessonsPreview() {
    MyLessons(
        navController = NavController(LocalContext.current).apply {
            createGraph(startDestination = "MyLessons") {}
        }
    )
}
