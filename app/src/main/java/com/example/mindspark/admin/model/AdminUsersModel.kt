package com.example.mindspark.admin.model

data class AdminUsersModel(
    val id: Int? = null,
    val fullName: String = "",
    val nickname: String = "",
    val email: String = "",
    val password: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val pin: String = "",
)