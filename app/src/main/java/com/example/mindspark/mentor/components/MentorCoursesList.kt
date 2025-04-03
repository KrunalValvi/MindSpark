package com.example.mindspark.mentor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.mentor.ui.CourseItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun MentorCoursesList(
    mentorId: Int, // Kept for backward compatibility
    refreshTrigger: Boolean,
    onEditCourse: (String) -> Unit,
    onRefresh: () -> Unit = {}
) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val scope = rememberCoroutineScope()
    var courses by remember { mutableStateOf<List<Pair<String, CourseModel>>>(emptyList()) }
    var localRefreshTrigger by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Combined refresh trigger that responds to both parent and local triggers
    val combinedRefreshTrigger = refreshTrigger || localRefreshTrigger

    LaunchedEffect(currentUser?.uid, combinedRefreshTrigger) {
        isLoading = true
        errorMessage = null
        
        try {
            if (currentUser == null) {
                errorMessage = "You must be logged in to view your courses"
                isLoading = false
                return@LaunchedEffect
            }
            
            // Log the current user's ID being used for filtering
            println("Querying for courses with creatorId: ${currentUser.uid}")

            // Fetch only courses created by the current user
            val snapshot = db.collection("courses")
                .whereEqualTo("creatorId", currentUser.uid)
                .get()
                .await()

            println("Courses created by current user: ${snapshot.size()}")

            // Map the results
            courses = snapshot.documents.mapNotNull { doc ->
                val course = doc.toObject(CourseModel::class.java)
                if (course != null) {
                    println("Found course: ${course.title} with creatorId: ${course.creatorId}")
                    Pair(doc.id, course)
                } else null
            }
            
            isLoading = false
        } catch (e: Exception) {
            println("Error fetching courses: ${e.message}")
            e.printStackTrace()
            errorMessage = "Error loading courses: ${e.message}"
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "My Courses",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            isLoading -> {
                Text(
                    text = "Loading your courses...",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "An error occurred",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            courses.isEmpty() -> {
                Text(
                    text = "You haven't created any courses yet",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            else -> {
                courses.forEach { (docId, course) ->
                    CourseItem(
                        course = course,
                        docId = docId,
                        onEditClick = onEditCourse,
                        onDelete = {
                            // Trigger local refresh when a course is deleted
                            localRefreshTrigger = !localRefreshTrigger
                            onRefresh()
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}