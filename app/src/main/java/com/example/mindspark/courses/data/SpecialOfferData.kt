package com.example.mindspark.courses.data

import androidx.compose.ui.graphics.Color
import com.example.mindspark.R
import com.example.mindspark.courses.model.SpecialOfferModel

object CardData {

    fun getCardDetails(): List<SpecialOfferModel> = listOf(
        SpecialOfferModel(
            title = "25% *Off",
            subtitle = "Flash Sale",
            description = "Limited time offer on all\nGraphic Design courses.\nEnds in 24 hours!",
            backgroundResId = R.drawable.background_card,
            buttonText = "Claim Now",
            cardColor = Color(0xFF0961F5),
            isFeatured = true,
            expiryDate = "Ends today"
        ),
        SpecialOfferModel(
            title = "New Course",
            subtitle = "Web Development",
            description = "Master React & Node.js\nwith our comprehensive\nfull-stack bootcamp",
            backgroundResId = R.drawable.background_card,
            buttonText = "Enroll Now",
            cardColor = Color(0xFF167F71),
            expiryDate = "Limited seats"
        ),
        SpecialOfferModel(
            title = "Free Workshop",
            subtitle = "UX/UI Design",
            description = "Join our live workshop\non modern interface\ndesign principles",
            backgroundResId = R.drawable.background_card,
            buttonText = "Register",
            cardColor = Color(0xFFFAC840),
            textColor = Color(0xFF202244)
        ),
        SpecialOfferModel(
            title = "Bundle Deal",
            subtitle = "Data Science",
            description = "Get 3 premium courses\nat the price of one.\nSave 65% today!",
            backgroundResId = R.drawable.background_card,
            buttonText = "View Bundle",
            cardColor = Color(0xFFFF6B6B),
            isFeatured = true
        ),
    )
}
