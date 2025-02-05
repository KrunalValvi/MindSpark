package com.example.mindspark.transactions.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mindspark.transactions.model.TransactionsModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getAllTransactions(): List<TransactionsModel> = listOf(
    TransactionsModel(
        title = "Build Personal Branding",
        course = "Web Designer",
        status = "Paid",
        amount = 49.99,
        date = "2024-02-01 10:30:00",
        paymentMethod = "Credit Card"
    ),
    TransactionsModel(
        title = "Mastering UI/UX",
        course = "Graphic Design",
        status = "Pending",
        amount = 79.99,
        date = "2024-02-03 15:45:00",
        paymentMethod = "PayPal"
    ),
    TransactionsModel(
        title = "Android Development Basics",
        course = "Mobile Development",
        status = "Paid",
        amount = 89.99,
        date = "2024-01-28 09:15:00",
        paymentMethod = "Debit Card"
    ),
    TransactionsModel(
        title = "Introduction to Kotlin",
        course = "Programming",
        status = "Failed",
        amount = 59.99,
        date = "2024-02-04 11:20:00",
        paymentMethod = "Credit Card"
    ),
    TransactionsModel(
        title = "Advanced Java Concepts",
        course = "Software Engineering",
        status = "Paid",
        amount = 99.99,
        date = "2024-01-25 14:00:00",
        paymentMethod = "PayPal"
    ),
    TransactionsModel(
        title = "Machine Learning Essentials",
        course = "Artificial Intelligence",
        status = "Pending",
        amount = 129.99,
        date = "2024-02-02 16:30:00",
        paymentMethod = "Credit Card"
    ),
    TransactionsModel(
        title = "Cybersecurity Fundamentals",
        course = "Network Security",
        status = "Paid",
        amount = 109.99,
        date = "2024-01-30 13:45:00",
        paymentMethod = "Google Pay"
    ),
    TransactionsModel(
        title = "Digital Marketing 101",
        course = "Marketing",
        status = "Paid",
        amount = 69.99,
        date = "2024-01-27 10:00:00",
        paymentMethod = "Debit Card"
    ),
    TransactionsModel(
        title = "Full-Stack Web Development",
        course = "Web Development",
        status = "Failed",
        amount = 149.99,
        date = "2024-02-03 09:30:00",
        paymentMethod = "Credit Card"
    ),
    TransactionsModel(
        title = "Python for Data Science",
        course = "Data Analytics",
        status = "Pending",
        amount = 89.99,
        date = "2024-02-04 12:15:00",
        paymentMethod = "PayPal"
    ),
    TransactionsModel(
        title = "Cloud Computing with AWS",
        course = "Cloud Technology",
        status = "Paid",
        amount = 119.99,
        date = "2024-01-29 11:00:00",
        paymentMethod = "Credit Card"
    )
)

// Helper function to calculate total amount spent
fun getTotalAmount(): Double {
    return getAllTransactions()
        .filter { it.status == "Paid" }
        .sumOf { it.amount }
}

// Helper function to get transaction counts by status
fun getTransactionCounts(): Map<String, Int> {
    val transactions = getAllTransactions()
    return mapOf(
        "Paid" to transactions.count { it.status == "Paid" },
        "Pending" to transactions.count { it.status == "Pending" },
        "Failed" to transactions.count { it.status == "Failed" }
    )
}

// Helper function to format currency
fun formatCurrency(amount: Double): String {
    return "₹${String.format("%.2f", amount)}"
}

// Helper function to format date
@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateString, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return dateTime.format(outputFormatter)
}