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

    suspend fun getPopularCourses(): List<CourseModel> = withContext(Dispatchers.IO) {
        try {
            val snapshot = coursesCollection.get().await()
            val courses = snapshot.documents.mapNotNull { document ->
                // When converting, add the document id to the model.
                document.toObject(CourseModel::class.java)?.copy(docId = document.id)?.also { course ->
                    Log.d(TAG, "Loaded course: ${course.title}")
                }
            }
            Log.i(TAG, "Successfully loaded ${courses.size} courses")
            courses
        } catch (e: FirebaseFirestoreException) {
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
            Log.e(TAG, "Unexpected error in getPopularCourses", e)
            emptyList()
        }
    }

    // New function that fetches a course by its document ID.
    suspend fun getCourseByDocId(docId: String): CourseModel? = withContext(Dispatchers.IO) {
        try {
            val doc = coursesCollection.document(docId).get().await()
            doc.toObject(CourseModel::class.java)?.copy(docId = doc.id)?.also { course ->
                Log.d(TAG, "Successfully retrieved course: ${course.title}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving course with docId: $docId", e)
            null
        }
    }
}
