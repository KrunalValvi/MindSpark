package com.example.mindspark.courses.model

data class FilterCategory(
    val title: String,
    val options: List<FilterItem>
)

data class FilterItem(
    val name: String,
    var isSelected: Boolean = false
)
