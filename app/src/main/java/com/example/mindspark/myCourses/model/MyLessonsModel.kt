package com.example.mindspark.myCourses.model

/**
 * Data classes to represent sections and lessons
 */
data class Section(
    val title: String,
    val totalTime: String,
    val lessons: List<LessonItem>
)

data class LessonItem(
    val number: String,
    val lessonTitle: String,
    val duration: String
)

// Define the data classes
data class CertificateData(
    val recipientName: String,
    val courseTitle: String,
    val issuedDate: String,
    val certificateId: String,
    val signerName: String
)