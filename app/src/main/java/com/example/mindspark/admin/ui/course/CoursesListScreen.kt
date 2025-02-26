package com.example.mindspark.admin.ui.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mindspark.admin.model.AdminCourseModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.mindspark.admin.components.AdminCourseCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesListScreen(
    onBack: () -> Unit,
    onCourseClick: (String?) -> Unit // courseId or null for new course
) {
    var courses by remember { mutableStateOf<List<Pair<String, AdminCourseModel>>>(emptyList()) }

    // Fetch courses from Firestore once.
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val snapshot = db.collection("courses").get().await()
        courses = snapshot.documents.mapNotNull { doc ->
            doc.toObject(AdminCourseModel::class.java)?.let { course ->
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
                        description = course.difficultylevel,
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
