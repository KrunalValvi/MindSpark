package com.example.mindspark.courses.data

import com.example.mindspark.courses.model.MentorCourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.courses.model.ReviewModel

object MentorData {

    // Get top mentors
    fun getTopMentors(): List<MentorModel> = listOf(
        MentorModel(
            id = 1, name = "Jiya", profession = "3D Design", courses = 10, students = 1500, ratings = 423,
            coursesList = listOf(
                MentorCourseModel("3D Modeling Basics", "Beginner", "Free", "4.5", "20", "5 Hours"),
                MentorCourseModel("Advanced 3D Design", "Advanced", "1200/-", "4.8", "30", "10 Hours")
            ),
            reviews = listOf(
                ReviewModel("Alex", "Great mentor!", "2 days ago"),
                ReviewModel("John", "Very helpful and knowledgeable.", "1 week ago")
            )
        ),        MentorModel(id = 2, name = "Aman", profession = "Web Developer", courses = 8, students = 1200, ratings = 52),
        MentorModel(id = 3, name = "Rahul", profession = "Android Developer", courses = 12, students = 2000, ratings = 55),
        MentorModel(id = 4, name = "Manev", profession = "UI/UX Designer", courses = 7, students = 950, ratings = 465),
        MentorModel(id = 5, name = "Raghav", profession = "Game Developer", courses = 15, students = 3000, ratings = 57),
        MentorModel(id = 6, name = "Mihir", profession = "Data Scientist", courses = 9, students = 1800, ratings = 42),
        MentorModel(id = 7, name = "Mohit", profession = "AI Specialist", courses = 11, students = 2200, ratings = 54),
        MentorModel(id = 8, name = "Sneha", profession = "Digital Marketing Specialist", courses = 6, students = 800, ratings = 44),
        MentorModel(id = 9, name = "Pooja", profession = "Graphic Designer", courses = 5, students = 600, ratings = 325),
        MentorModel(id = 10, name = "Rajesh", profession = "Cloud Architect", courses = 13, students = 2500, ratings = 525),
        MentorModel(id = 11, name = "Neha", profession = "Cybersecurity Expert", courses = 10, students = 1700, ratings = 452),
        MentorModel(id = 12, name = "Siddharth", profession = "Blockchain Developer", courses = 8, students = 1400, ratings = 552),
        MentorModel(id = 13, name = "Kriti", profession = "Product Manager", courses = 7, students = 1000, ratings = 4252),
        MentorModel(id = 14, name = "Arjun", profession = "DevOps Engineer", courses = 9, students = 1600, ratings = 42)
    )
}