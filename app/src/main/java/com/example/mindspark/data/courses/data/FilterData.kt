package com.example.mindspark.data.courses.data

import com.example.mindspark.data.courses.model.FilterModel

/**
 * FilterData.kt
 *
 * Provides data for course filters.
 */
class FilterData {
    val filters = listOf(
        FilterModel("Difficulty", listOf("Beginner", "Intermediate", "Advanced")),
        FilterModel("Price", listOf("Free", "Paid"))
    )
}
