package com.example.mindspark.courses.model

import androidx.compose.ui.graphics.Color

//Special Offers Screen
data class SpecialOfferModel(
    val title: String,
    val subtitle: String,
    val description: String,
    val backgroundResId: Int,
    val buttonText: String = "Explore",
    val actionUrl: String = "",
    val cardColor: Color = Color(0xFF1A6EFC),
    val textColor: Color = Color.White,
    val isFeatured: Boolean = false,
    val expiryDate: String = ""
)