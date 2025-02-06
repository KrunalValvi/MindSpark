package com.example.mindspark.bookmark.data

import com.example.mindspark.bookmark.model.BookMarkModel

fun getAllTransactions(): List<BookMarkModel> = listOf(
    BookMarkModel(
        title = "Build Personal Branding",
        course = "Web Designer",
        status = "Paid"
    ),
    BookMarkModel(
        title = "Mastering UI/UX",
        course = "Graphic Design",
        status = "Pending"
    ),
    BookMarkModel(
        title = "Android Development Basics",
        course = "Mobile Development",
        status = "Paid"
    ),
    BookMarkModel(
        title = "Introduction to Kotlin",
        course = "Programming",
        status = "Failed"
    ),
    BookMarkModel(
        title = "Advanced Java Concepts",
        course = "Software Engineering",
        status = "Paid"
    ),
    BookMarkModel(
        title = "Machine Learning Essentials",
        course = "Artificial Intelligence",
        status = "Pending"
    ),
    BookMarkModel(
        title = "Cybersecurity Fundamentals",
        course = "Network Security",
        status = "Paid"
    ),
    BookMarkModel(
        title = "Digital Marketing 101",
        course = "Marketing",
        status = "Paid"
    ),
    BookMarkModel(
        title = "Full-Stack Web Development",
        course = "Web Development",
        status = "Failed"
    ),
    BookMarkModel(
        title = "Python for Data Science",
        course = "Data Analytics",
        status = "Pending"
    ),
    BookMarkModel(
        title = "Cloud Computing with AWS",
        course = "Cloud Technology",
        status = "Paid"
    )
)
