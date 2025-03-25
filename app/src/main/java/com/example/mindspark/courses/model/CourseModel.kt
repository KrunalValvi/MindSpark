package com.example.mindspark.courses.model

import androidx.annotation.DrawableRes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.coroutines.tasks.await

data class FeatureModel(
    val description: String,
    @DrawableRes val iconRes: Int // Use drawable resource for icon if needed
)

@IgnoreExtraProperties
data class CourseModel(
    @PropertyName("course_id") val id: Int = 0,
    val category: String = "",
    val title: String = "",
    val price: String = "",
    val rating: String = "",
    val students: String = "",
    val imageRes: String = "",
    val videos: Any = "", // Kept flexible to handle different types
    val hours: String = "",
    val about: String = "",
    val difficultyLevel: String = "",
    val certification: Boolean = false,
    val language: String = "",
    val mentorIds: List<Int> = emptyList(),
    val features: List<String> = emptyList(),
    var isBookmarked: Boolean = false
) {
    // Conversion method to handle different video field types
    fun getVideosAsString(): String {
        return when (videos) {
            is String -> videos
            is Number -> videos.toString()
            else -> ""
        }
    }
}

data class Review(
    val name: String,
    val review: String,
    val likes: String,
    val timeAgo: String
)

data class VideoItem(
    val videoId: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String
)

// You can keep your CourseRepository here if you want an alternative method to fetch data.
class CourseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val coursesCollection = db.collection("courses")

    suspend fun getCourses(): List<CourseModel> {
        return try {
            val snapshot = coursesCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(CourseModel::class.java) }
        } catch (e: Exception) {
            emptyList() // Return empty list if there's an error
        }
    }
}
