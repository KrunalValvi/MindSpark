package com.example.mindspark.admin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminYoutubeModel(
    val category: String = "",
    val title: String = "",
    val price: String = "",
    val rating: String = "",
    val students: String = "",
    val videos: String = "",
    val hours: String = "",
    val difficultyLevel: String = "",
    val language: String = "",
    val certification: String = "",
    val about: String = "",
    val mentorIds: String = "", // e.g., comma-separated IDs
    val imageRes: String = "",
    val features: String = ""
) : Parcelable
