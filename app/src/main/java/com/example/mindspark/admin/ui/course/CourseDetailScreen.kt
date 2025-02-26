package com.example.mindspark.admin.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.admin.model.AdminCourseModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String?,
    onBack: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

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

    // Fetch course details if editing an existing course
    LaunchedEffect(courseId) {
        if (courseId != null) {
            try {
                val doc = db.collection("courses").document(courseId).get().await()
                val course = doc.toObject(AdminCourseModel::class.java)
                course?.let {
                    category = it.category
                    title = it.title
                    price = it.price
                    rating = it.rating ?: ""
                    students = it.students ?: ""
                    videos = it.videos?.toString() ?: ""
                    hours = it.hours ?: ""
                    about = it.about
                    difficultyLevel = it.difficultylevel
                    certification = it.certification
                    language = it.language
                    features = it.features.joinToString(", ")
                    mentorIds = it.mentorIds.joinToString(", ")
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
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Certification Available")
                Switch(checked = certification, onCheckedChange = { certification = it })
            }

            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = language, onValueChange = { language = it }, label = { Text("Language") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = difficultyLevel, onValueChange = { difficultyLevel = it }, label = { Text("Difficulty Level") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = features, onValueChange = { features = it }, label = { Text("Features (comma-separated)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = mentorIds, onValueChange = { mentorIds = it }, label = { Text("Mentor IDs (comma-separated)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val newCourse = AdminCourseModel(
                            category = category,
                            title = title,
                            price = price,
                            rating = rating.takeIf { it.isNotEmpty() },
                            students = students.takeIf { it.isNotEmpty() },
                            videos = videos.toIntOrNull(),
                            hours = hours,
                            about = about,
                            difficultylevel = difficultyLevel,
                            certification = certification,
                            language = language,
                            features = features.split(",").map { it.trim() },
                            mentorIds = mentorIds.split(",").mapNotNull { it.trim().toIntOrNull() }
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
