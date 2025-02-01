package com.example.mindspark.courses.model

import androidx.annotation.DrawableRes

//Home Screen
enum class CourseCategory(val value: String) {
    All("All"),
    GraphicDesign("Graphic Design"),
    Design("3D Design"),
    WebDevelopment("Web Development"),
    SEOMarketing("Digital Marketing"),
    Programming("App Development"),
    UIUXDesign("UI/UX Design"),
    GameDevelopment("Game Development"),
    DataScience("Data Science"),
    AIML("AI & Machine Learning"),
    CloudComputing("Cloud Computing"),
    CyberSecurity("Cybersecurity"),
    Blockchain("Blockchain"),
    ProductManagement("Product Management"),
    DevOps("DevOps")
}

data class FeatureModel(
    val description: String,
    @DrawableRes val iconRes: Int // Use drawable resource for icon
)

//Courses Screen
data class CourseModel(
    val category: String,
    val title: String,
    val price: String,
    val rating: String,
    val students: String,

    val mentorId: Int,
    val videos: String,
    val hours: String,
    val about: String,
    val difficultyLevel: String,
    val certification: String,
    val language: String,

    val features: List<FeatureModel>
)
