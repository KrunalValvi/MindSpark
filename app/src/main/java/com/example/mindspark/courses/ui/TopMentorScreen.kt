package com.example.mindspark.courses.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.home.components.TopMentorsListVertical
import com.example.mindspark.courses.data.MentorData
import com.example.mindspark.courses.model.MentorModel
import com.google.firebase.firestore.FirebaseFirestore

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun TopMentorScreen(navController: NavController) {
    // State to track loading and potential errors
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Explicitly log the screen was entered
    Log.d("TopMentorScreen", "TopMentorScreen composed")

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Top Mentors",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Show loading indicator if still loading
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF0961F5)
                )
                Text(
                    text = "Loading top mentors...",
                    color = Color(0xFF202244),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            // Show error message if there was an error
            else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Unknown error occurred",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            // Show mentors list
            else {
                // Mentors List - the list will be fetched inside the component
                TopMentorsListVertical(
                    mentors = MentorData.getTopMentors(), // This is just a fallback
                    onMentorClick = { mentor ->
                        Log.d("TopMentorScreen", "Mentor clicked: ${mentor.name}, ID: ${mentor.id}")
                        navController.navigate("SingleMentorDetails/${mentor.id}")
                    }
                )
            }

            // Set loading to false after some delay to allow the component to fetch mentors
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1000)
                isLoading = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopMentorScreenPreview(){
    TopMentorScreen(navController = NavController(LocalContext.current))
}