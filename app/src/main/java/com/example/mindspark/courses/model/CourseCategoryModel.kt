package com.example.mindspark.courses.model

import com.example.mindspark.R
import com.example.mindspark.home.model.CategoryItem

object CategoryData {
    val categories = listOf(
        CategoryItem("3D Design", R.drawable.ca_3d_design),
        CategoryItem("Graphic Design", R.drawable.ca_graphic_design),
        CategoryItem("Web Development", R.drawable.ca_web_development),
        CategoryItem("SEO & Marketing", R.drawable.ca_seo_marketing),
        CategoryItem("Finance & Accounting", R.drawable.ca_finance),
        CategoryItem("Personal Development", R.drawable.ca_personal_dev),
        CategoryItem("Office Productivity", R.drawable.ca_office_productivity),
        CategoryItem("HR Management", R.drawable.ca_hr_management)
    )
}

// Home Screen
enum class CourseCategory(val value: String) {
    All("All"),
    Mathematics("Mathematics"),
    Physics("Physics"),
    Chemistry("Chemistry"),
    Biology("Biology"),
    ComputerScience("Computer Science"),
    History("History"),
    Literature("Literature"),
    Art("Art"),
    Economics("Economics"),
    Psychology("Psychology")
}