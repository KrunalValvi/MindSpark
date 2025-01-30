package com.example.mindspark.courses.data

import com.example.mindspark.R
import com.example.mindspark.courses.model.SpecialOfferModel

object CardData {
    fun getCardDetails(): List<SpecialOfferModel> = listOf(
        SpecialOfferModel(
            title = "25% *Off",
            subtitle = "Today's Special",
            description = "Get a Discount for Every\nCourse Order only Valid for\nToday.!",
            backgroundResId = R.drawable.background_card
        ),
        SpecialOfferModel(
            title = "50% *Off",
            subtitle = "Today's Special",
            description = "Get a Discount for Every\nCourse Order only Valid for\nToday.!",
            backgroundResId = R.drawable.background_card
        ),
        SpecialOfferModel(
            title = "75% *Off",
            subtitle = "Today's Special",
            description = "Get a Discount for Every\nCourse Order only Valid for\nToday.!",
            backgroundResId = R.drawable.background_card
        ),
        SpecialOfferModel(
            title = "2500% *Off",
            subtitle = "Today's Special",
            description = "Get a Discount for Every\nCourse Order only Valid for\nToday.!",
            backgroundResId = R.drawable.background_card
        ),
    )
}
