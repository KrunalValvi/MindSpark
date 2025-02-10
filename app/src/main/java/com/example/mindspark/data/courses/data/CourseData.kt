package com.example.mindspark.data.courses.data

import com.example.mindspark.data.courses.model.CourseModel

/**
 * CourseData.kt
 *
 * Handles data operations for course information.
 */
class CourseData {
    private val courses = mutableListOf<CourseModel>()

    fun getCourses(): List<CourseModel> = courses

    fun addCourse(course: CourseModel) {
        courses.add(course)
    }
}
