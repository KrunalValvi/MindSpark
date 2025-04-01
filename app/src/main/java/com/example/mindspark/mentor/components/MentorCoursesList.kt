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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.mentor.ui.CourseItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun MentorCoursesList(
    mentorId: Int,
    refreshTrigger: Boolean,
    onEditCourse: (String) -> Unit,
    onRefresh: () -> Unit = {}
) {
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()
    var courses by remember { mutableStateOf<List<Pair<String, CourseModel>>>(emptyList()) }
    var localRefreshTrigger by remember { mutableStateOf(false) }

    // Combined refresh trigger that responds to both parent and local triggers
    val combinedRefreshTrigger = refreshTrigger || localRefreshTrigger

    LaunchedEffect(mentorId, combinedRefreshTrigger) {
        try {
            // First, log the mentor ID being used
            println("Querying for courses with mentorId: $mentorId")

            // Perform a more general query first to see all courses
            val allCoursesSnapshot = db.collection("courses").get().await()
            println("Total courses in database: ${allCoursesSnapshot.size()}")

            // Now perform the filtered query
            val snapshot = try {
                db.collection("courses")
                    .whereArrayContains("mentorIds", mentorId)
                    .get()
                    .await()
            } catch (e: Exception) {
                // If that fails, try with String
                db.collection("courses")
                    .whereArrayContains("mentorIds", mentorId.toString())
                    .get()
                    .await()
            }

            println("Courses matching mentorId $mentorId: ${snapshot.size()}")

            // Map the results
            courses = snapshot.documents.mapNotNull { doc ->
                val course = doc.toObject(CourseModel::class.java)
                if (course != null) {
                    println("Found course: ${course.title} with mentorIds: ${course.mentorIds}")
                    Pair(doc.id, course)
                } else null
            }
        } catch (e: Exception) {
            println("Error fetching courses: ${e.message}")
            e.printStackTrace()
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

        if (courses.isEmpty()) {
            Text(
                text = "You haven't created any courses yet",
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
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