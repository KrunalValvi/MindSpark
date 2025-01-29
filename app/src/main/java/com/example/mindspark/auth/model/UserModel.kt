package com.example.mindspark.auth.model

data class UserModel(
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
    val nickname: String = "",
    val dateOfBirth: String = "",
    val phoneNumber: String = "",
    val gender: String = "",
    val isTermsAccepted: Boolean = false
)

data class LoginCredentials(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false
)