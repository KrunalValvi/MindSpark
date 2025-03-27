package com.example.mindspark.admin.ui.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.fetchYoutubePlaylistData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCourseDetailScreen(
    courseId: String?,
    onBack: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    // Mutable state variables for course details
    var category by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var students by remember { mutableStateOf("") }
    var videos by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var difficultyLevel by remember { mutableStateOf("") }
    var certification by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("") }
    var features by remember { mutableStateOf("") }
    var mentorIds by remember { mutableStateOf("") }
    var imageRes by remember { mutableStateOf("") }
    var youtubePlaylistLink by remember { mutableStateOf("") }

    // Fetch course details if editing an existing course
    LaunchedEffect(courseId) {
        if (courseId != null) {
            try {
                val doc = db.collection("courses").document(courseId).get().await()
                val course = doc.toObject(CourseModel::class.java)
                course?.let {
                    category = it.category
                    title = it.title
                    price = it.price
                    rating = it.rating
                    students = it.students
                    imageRes = it.imageRes
                    videos = it.getVideosAsString() // Use the conversion method
                    hours = it.hours
                    about = it.about
                    difficultyLevel = it.difficultyLevel // Corrected property name
                    certification = it.certification
                    language = it.language
                    features = it.features.joinToString(", ")
                    mentorIds = it.mentorIds.joinToString(", ")
                    // Optionally, you can also set youtubePlaylistLink here if stored
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(if (courseId != null) "Edit Course" else "New Course") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = videos, onValueChange = { videos = it }, label = { Text("Videos (Number)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = hours, onValueChange = { hours = it }, label = { Text("Duration Hours") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = about, onValueChange = { about = it }, label = { Text("About Course") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = rating, onValueChange = { rating = it }, label = { Text("Rating") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = students, onValueChange = { students = it }, label = { Text("Students") }, modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Certification Available")
                Switch(checked = certification, onCheckedChange = { certification = it })
            }

            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = language, onValueChange = { language = it }, label = { Text("Language") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = difficultyLevel, onValueChange = { difficultyLevel = it }, label = { Text("Difficulty Level") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = features, onValueChange = { features = it }, label = { Text("Features (comma-separated)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = mentorIds, onValueChange = { mentorIds = it }, label = { Text("Mentor IDs (comma-separated)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = imageRes, onValueChange = { imageRes = it }, label = { Text("Image URL") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = youtubePlaylistLink,
                onValueChange = { youtubePlaylistLink = it },
                label = { Text("YouTube Playlist Link") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        // Fetch the playlist videos on a background thread
                        val playlistVideos = fetchYoutubePlaylistData(youtubePlaylistLink)

                        // Create a new CourseModel including the fetched playlistVideos
                        val newCourse = CourseModel(
                            category = category,
                            title = title,
                            price = price,
                            rating = rating,
                            students = students,
                            imageRes = imageRes,
                            videos = videos.toIntOrNull() ?: 0, // Adjusted type if CourseModel expects an Int
                            hours = hours,
                            about = about,
                            difficultyLevel = difficultyLevel,
                            certification = certification,
                            language = language,
                            features = features.split(",").map { it.trim() },
                            mentorIds = mentorIds.split(",").mapNotNull { it.trim().toIntOrNull() },
                            playlistVideos = playlistVideos // Set the playlist videos field
                        )

                        try {
                            if (courseId == null) {
                                db.collection("courses").add(newCourse).await()
                            } else {
                                db.collection("courses").document(courseId).set(newCourse).await()
                            }
                            onBack()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
