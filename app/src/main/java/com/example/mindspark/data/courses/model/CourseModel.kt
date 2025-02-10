package com.example.mindspark.data.courses.model

/**
 * CourseModel.kt
 *
 * Model representing a course.
 */
data class CourseModel(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val mentorId: String
)
