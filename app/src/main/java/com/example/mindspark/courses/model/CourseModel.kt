package com.example.mindspark.courses.model

//Courses Screen
data class CourseModel(
    val category: String,
    val title: String,
    val price: String,
    val rating: String,
    val students: String,

    val id: Int,
    val videos: String,
    val hours: String,
    val about: String,
    val difficultyLevel: String,
    val certification: String,
    val language: String
)