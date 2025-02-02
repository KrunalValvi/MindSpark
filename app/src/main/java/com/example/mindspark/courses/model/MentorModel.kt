package com.example.mindspark.courses.model

data class MentorModel(
    val id: Int,
    val name: String,
    val profession: String,

    val courses: Int,
    val students: Int,
    val ratings: Int
)