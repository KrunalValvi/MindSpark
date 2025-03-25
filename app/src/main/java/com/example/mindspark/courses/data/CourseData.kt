package com.example.mindspark.courses.data

import android.annotation.SuppressLint
import com.example.mindspark.courses.model.CourseModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object CourseData {

    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
    val coursesCollection = db.collection("courses")

    // Fetch all courses from Firestore
    suspend fun getPopularCourses(): List<CourseModel> {
        return try {
            val snapshot = coursesCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(CourseModel::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Fetch a single course by its id from Firestore
    suspend fun getCourseById(courseId: Int): CourseModel? {
        return try {
            val snapshot = coursesCollection
                .whereEqualTo("id", courseId)
                .get()
                .await()
            snapshot.documents.firstOrNull()?.toObject(CourseModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Fetch courses by a mentor's id from Firestore
    suspend fun getCoursesByMentorId(mentorId: Int): List<CourseModel> {
        return try {
            val snapshot = coursesCollection
                .whereArrayContains("mentorIds", mentorId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(CourseModel::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
