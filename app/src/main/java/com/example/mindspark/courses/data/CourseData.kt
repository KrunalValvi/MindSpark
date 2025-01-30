package com.example.mindspark.courses.data

import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel

object CourseData {

    fun getAllCategories(): List<CourseCategory> = CourseCategory.values().toList()

    fun getPopularCourses(): List<CourseModel> = listOf(
        CourseModel(
            category = "Graphic Design",
            title = "Graphic Design Advanced",
            price = "850/-",
            rating = "4.2",
            students = "7830 Std"
        ),
        CourseModel(
            category = "Programming",
            title = "Kotlin for Beginners",
            price = "1200/-",
            rating = "4.5",
            students = "5240 Std"
        ),
        CourseModel(
            category = "3D Design",
            title = "Master 3D Animation with Blender",
            price = "1500/-",
            rating = "4.8",
            students = "6500 Std"
        ),
        CourseModel(
            category = "SEO & Marketing",
            title = "SEO for Beginners: Boost Your Website Traffic",
            price = "1000/-",
            rating = "4.7",
            students = "4820 Std"
        ),
        CourseModel(
            category = "Web Development",
            title = "Full-Stack Web Development with React & Node",
            price = "2000/-",
            rating = "4.9",
            students = "7100 Std"
        ),
        CourseModel(
            category = "HR Management",
            title = "Strategic HR Management and Leadership",
            price = "1800/-",
            rating = "4.6",
            students = "5400 Std"
        ),
        CourseModel(
            category = "Data Science",
            title = "Python for Data Science and Machine Learning",
            price = "2200/-",
            rating = "4.9",
            students = "8600 Std"
        ),
        CourseModel(
            category = "App Development",
            title = "Android App Development with Kotlin",
            price = "2500/-",
            rating = "4.8",
            students = "7300 Std"
        )
    )
}