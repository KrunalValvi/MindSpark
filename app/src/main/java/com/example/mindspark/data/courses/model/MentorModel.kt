package com.example.mindspark.courses.model

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
    val name: String,
    val profession: String,
    val courses: Int,
    val students: Int,
    val ratings: Int,
    val coursesList: List<MentorCourseModel>,
    val reviews: List<ReviewModel>,
    val imageRes: Int
)