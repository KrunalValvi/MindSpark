package com.example.mindspark.data.courses.data

import com.example.mindspark.data.courses.model.MentorModel

/**
 * MentorData.kt
 *
 * Provides data for mentor information.
 */
class MentorData {
    val mentors = listOf(
        MentorModel("1", "John Doe", "Android Development", 5),
        MentorModel("2", "Jane Doe", "UI/UX Design", 3)
    )
}
