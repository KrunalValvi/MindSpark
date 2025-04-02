package com.example.mindspark.courses.model

data class CourseReviewModel(
    val reviewerName: String,
    val reviewText: String,
    val date: String,
    val likes: Int = 0,
    val id: String,
    val reviewerImageUrl: String
)