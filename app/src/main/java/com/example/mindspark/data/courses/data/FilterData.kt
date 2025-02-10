package com.example.mindspark.courses.data

import com.example.mindspark.courses.model.FilterCategory
import com.example.mindspark.courses.model.FilterItem

object FilterData {

    fun getFilterOptions(): List<FilterCategory> {
        return listOf(
            FilterCategory("SubCategories:", listOf(
                FilterItem("3D Design"),
                FilterItem("Web Development"),
                FilterItem("3D Animation"),
                FilterItem("Graphic Design"),
                FilterItem("SEO & Marketing"),
                FilterItem("Arts & Humanities")
            )),
            FilterCategory("Levels:", listOf(
                FilterItem("All Levels"),
                FilterItem("Beginners"),
                FilterItem("Intermediate"),
                FilterItem("Expert")
            )),
            FilterCategory("Price:", listOf(
                FilterItem("Paid"),
                FilterItem("Free")
            )),
            FilterCategory("Features:", listOf(
                FilterItem("All Caption"),
                FilterItem("Quizzes"),
                FilterItem("Coding Exercise"),
                FilterItem("Practice Tests")
            )),
            FilterCategory("Rating:", listOf(
                FilterItem("4.5 & Up Above"),
                FilterItem("4.0 & Up Above"),
                FilterItem("3.5 & Up Above")
            )),
            FilterCategory("Video Durations:", listOf(
                FilterItem("0-2 Hours"),
                FilterItem("3-6 Hours"),
                FilterItem("7-16 Hours"),
                FilterItem("17+ Hours")
            ))
        )
    }
}