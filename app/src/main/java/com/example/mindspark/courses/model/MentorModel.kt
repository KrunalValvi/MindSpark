package com.example.mindspark.courses.model

import android.graphics.Bitmap

data class MentorCourseModel(
    val id: Int, // Unique identifier for the course
    val title: String,
    val level: String,
    val price: String,
    val rating: String,
    val videos: String,
    val hours: String
)

data class ReviewModel(
    val reviewerName: String,
    val reviewText: String,
    val date: String
)

data class MentorModel(
    val id: Int, // Unique identifier for the mentor
    val userId: String = "", // Firebase user ID
    val name: String,
    val profession: String,
    val courses: Int,
    val students: Int,
    val ratings: Int,
    val coursesList: List<MentorCourseModel> = emptyList(),
    val reviews: List<ReviewModel> = emptyList(),
    val imageRes: Int = 0, // Default resource ID for local images
    val profileImageUrl: String = "" // Added for Firebase storage URL
) {
    // Helper method to convert Firestore document to MentorModel
    companion object {
        fun fromFirestore(document: Map<String, Any>, defaultImageRes: Int): MentorModel {
            return MentorModel(
                id = (document["id"] as? Long)?.toInt() ?: 0,
                userId = document["userId"] as? String ?: "",
                name = document["fullName"] as? String ?: "Unknown",
                profession = document["profession"] as? String ?: "Mentor",
                courses = (document["courses"] as? Long)?.toInt() ?: 0,
                students = (document["students"] as? Long)?.toInt() ?: 0,
                ratings = (document["ratings"] as? Long)?.toInt() ?: 0,
                profileImageUrl = document["profileImageUrl"] as? String ?: "",
                imageRes = defaultImageRes
            )
        }
    }
}