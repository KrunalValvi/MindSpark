package com.example.mindspark.courses.data

import com.example.mindspark.courses.model.MentorModel

object MentorData {

    // Get top mentors
    fun getTopMentors(): List<MentorModel> = listOf(
        MentorModel(id = 1, name = "Jiya", profession = "3D Design"),
        MentorModel(id = 2, name = "Aman", profession = "Web Developer"),
        MentorModel(id = 3, name = "Rahul", profession = "Android Developer"),
        MentorModel(id = 4, name = "Manev", profession = "UI/UX Designer"),
        MentorModel(id = 5, name = "Raghav", profession = "Game Developer"),
        MentorModel(id = 6, name = "Mihir", profession = "Data Scientist"),
        MentorModel(id = 7, name = "Mohit", profession = "AI Specialist"),
        MentorModel(id = 8, name = "Sneha", profession = "Digital Marketing Specialist"),
        MentorModel(id = 9, name = "Pooja", profession = "Graphic Designer"),
        MentorModel(id = 10, name = "Rajesh", profession = "Cloud Architect"),
        MentorModel(id = 11, name = "Neha", profession = "Cybersecurity Expert"),
        MentorModel(id = 12, name = "Siddharth", profession = "Blockchain Developer"),
        MentorModel(id = 13, name = "Kriti", profession = "Product Manager"),
        MentorModel(id = 14, name = "Arjun", profession = "DevOps Engineer")
    )
}