package com.example.mindspark.transactions.model

data class TransactionsModel(
    val title: String,
    val course: String,
    val status: String,
    val amount: Double,
    val date: String,
    val paymentMethod: String
)