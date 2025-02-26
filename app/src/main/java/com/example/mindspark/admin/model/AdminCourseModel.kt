package com.example.mindspark.admin.model

data class AdminCourseModel(
    val id: Int? = null,
    val category: String = "",

    val title: String = "",
    val price: String = "",

    val rating: String? = null,
    val students: String? = null,

//    val imageRes: String? = "",
    val videos: Int? = null,

    val hours: String? = "",
    val about: String = "",

    val difficultylevel: String = "",
    val certification: Boolean = false,

    val language: String = "",
    val features: List<String> = emptyList(),

    val mentorIds: List<Int> = emptyList(),
)