package com.example.mindspark.courses.data

import android.annotation.SuppressLint
import android.util.Log
import com.example.mindspark.courses.model.CourseModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object CourseData {
    private const val TAG = "CourseData"

    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()
    private val coursesCollection = db.collection("courses")

    /**
     * Fetch popular courses with enhanced error handling and logging
     * @return List of CourseModel or empty list if no courses found
     */
    suspend fun getPopularCourses(): List<CourseModel> = withContext(Dispatchers.IO) {
        try {
            val snapshot = coursesCollection.get().await()
            val courses = snapshot.documents.mapNotNull { document ->
                document.toObject(CourseModel::class.java)?.also { course ->
                    // Optional: Log each course for debugging
                    Log.d(TAG, "Loaded course: ${course.title}")
                }
            }

            // Log total courses and provide feedback
            Log.i(TAG, "Successfully loaded ${courses.size} courses")
            courses
        } catch (e: FirebaseFirestoreException) {
            // More specific error handling for Firestore
            when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    Log.e(TAG, "Permission denied when fetching courses", e)
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    Log.e(TAG, "Firestore service is unavailable", e)
                else ->
                    Log.e(TAG, "Error fetching courses", e)
            }
            emptyList()
        } catch (e: Exception) {
            // Catch-all for any other unexpected errors
            Log.e(TAG, "Unexpected error in getPopularCourses", e)
            emptyList()
        }
    }

    /**
     * Fetch a single course by its ID with more detailed error handling
     * @param courseId Unique identifier of the course
     * @return CourseModel or null if not found
     */
    suspend fun getCourseById(courseId: Int): CourseModel? = withContext(Dispatchers.IO) {
        try {
            val snapshot = coursesCollection
                .whereEqualTo("id", courseId)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(CourseModel::class.java)?.also { course ->
                Log.d(TAG, "Successfully retrieved course: ${course.title}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving course with ID: $courseId", e)
            null
        }
    }

    /**
     * Fetch courses associated with a specific mentor
     * @param mentorId Unique identifier of the mentor
     * @return List of courses taught by the mentor
     */
    suspend fun getCoursesByMentorId(mentorId: Int): List<CourseModel> = withContext(Dispatchers.IO) {
        try {
            val snapshot = coursesCollection
                .whereArrayContains("mentorIds", mentorId)
                .get()
                .await()

            val courses = snapshot.documents.mapNotNull { document ->
                document.toObject(CourseModel::class.java)
            }

            Log.i(TAG, "Found ${courses.size} courses for mentor $mentorId")
            courses
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving courses for mentor: $mentorId", e)
            emptyList()
        }
    }
}