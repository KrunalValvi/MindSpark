package com.example.mindspark.myCourses.data

import androidx.compose.runtime.mutableStateListOf
import com.example.mindspark.myCourses.model.CertificateData
import com.example.mindspark.myCourses.model.LessonItem
import com.example.mindspark.myCourses.model.Section

/**
 * Sample data or data-fetching logic goes here.
 * In a real app, you might fetch from a ViewModel or repository.
 */

fun getSampleSections(): List<Section> {
    return listOf(
        Section(
            title = "Introduction",
            totalTime = "25 Mins",
            lessons = listOf(
                LessonItem("01", "Why Using 3D Blender", "15 Mins"),
                LessonItem("02", "3D Blender Installation", "10 Mins")
            )
        ),
        Section(
            title = "Graphic Design",
            totalTime = "125 Mins",
            lessons = listOf(
                LessonItem("03", "Take a Look Blender Interface", "25 Mins"),
                LessonItem("04", "The Basic of 3D Modelling", "30 Mins"),
                LessonItem("05", "Shading and Lighting", "40 Mins"),
                LessonItem("06", "More Graphic Design Tools", "30 Mins")
            )
        )
    )
}

val sampleData = CertificateData(
    recipientName = "Alex Johnson",
    courseTitle = "3D Design Illustration Course",
    issuedDate = "November 24, 2022",
    certificateId = "SK24568086",
    signerName = "Virginia M. Patterson"
)
