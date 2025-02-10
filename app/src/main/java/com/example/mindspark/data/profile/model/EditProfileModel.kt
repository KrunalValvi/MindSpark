package com.example.mindspark.data.profile.model

/**
 * EditProfileModel.kt
 *
 * Model for editing user profile details.
 */
data class EditProfileModel(
    val userId: String,
    val name: String,
    val email: String,
    val profilePictureUrl: String? = null
)
