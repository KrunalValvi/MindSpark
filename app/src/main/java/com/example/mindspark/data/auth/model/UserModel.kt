package com.example.mindspark.data.auth.model

/**
 * UserModel.kt
 *
 * Model representing a user.
 */
data class UserModel(
    val id: String,
    val username: String,
    val email: String,
    val token: String? = null // Optional authentication token
)
