package com.example.mindspark.transactions.data

import com.example.mindspark.transactions.model.TransactionsModel

fun getAllTransactions(): List<TransactionsModel> = listOf(
    TransactionsModel(
        title = "Build Personal Branding",
        course = "Web Designer",
//        status = "Paid"
    ),
    TransactionsModel(
        title = "Mastering UI/UX",
        course = "Graphic Design",
//        status = "Pending"
    ),
    TransactionsModel(
        title = "Android Development Basics",
        course = "Mobile Development",
//        status = "Paid"
    ),
    TransactionsModel(
        title = "Introduction to Kotlin",
        course = "Programming",
//        status = "Failed"
    ),
    TransactionsModel(
        title = "Advanced Java Concepts",
        course = "Software Engineering",
//        status = "Paid"
    ),
    TransactionsModel(
        title = "Machine Learning Essentials",
        course = "Artificial Intelligence",
//        status = "Pending"
    ),
    TransactionsModel(
        title = "Cybersecurity Fundamentals",
        course = "Network Security",
//        status = "Paid"
    ),
    TransactionsModel(
        title = "Digital Marketing 101",
        course = "Marketing",
//        status = "Paid"
    ),
    TransactionsModel(
        title = "Full-Stack Web Development",
        course = "Web Development",
//        status = "Failed"
    ),
    TransactionsModel(
        title = "Python for Data Science",
        course = "Data Analytics",
//        status = "Pending"
    ),
    TransactionsModel(
        title = "Cloud Computing with AWS",
        course = "Cloud Technology",
//        status = "Paid"
    )
)
