package com.example.mindspark.data.courses.model

/**
 * FilterModel.kt
 *
 * Model representing course filter settings.
 */
data class FilterModel(
    val filterName: String,
    val options: List<String>
)
