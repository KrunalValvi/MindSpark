package com.example.mindspark.admin.ui.course

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.admin.components.AdminCourseCard
import com.example.mindspark.courses.model.CourseModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCoursesListScreen(
    onBack: () -> Unit,
    onCourseClick: (String?) -> Unit // courseId or null for new course
) {
    var courses by remember { mutableStateOf<List<Pair<String, CourseModel>>>(emptyList()) }

    // Fetch courses from Firestore once.
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val snapshot = db.collection("courses").get().await()
        courses = snapshot.documents.mapNotNull { doc ->
            doc.toObject(CourseModel::class.java)?.let { course ->
                doc.id to course
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Courses (${courses.size})") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(courses) { (id, course) ->
                    AdminCourseCard(
                        category = course.category,
                        title = course.title,
                        description = course.difficultyLevel,
                        imageUrl = course.imageRes,
                        rating = course.rating,
                        students = course.students,
                        onClick = { onCourseClick(id) }
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = { onCourseClick(null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Course")
        }
    }
}