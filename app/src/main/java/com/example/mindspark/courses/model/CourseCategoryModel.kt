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
    GraphicDesign("Graphic Design"),
    Design("3D Design"),
    WebDevelopment("Web Development"),
    SEOMarketing("Digital Marketing"),
    Programming("App Development"),
    Marketing("Marketing"),
//    GameDevelopment("Game Development"),
//    DataScience("Data Science"),
//    AIML("AI & Machine Learning"),
//    CloudComputing("Cloud Computing"),
//    CyberSecurity("Cybersecurity"),
//    Blockchain("Blockchain"),
//    ProductManagement("Product Management"),
//    DevOps("DevOps")
}