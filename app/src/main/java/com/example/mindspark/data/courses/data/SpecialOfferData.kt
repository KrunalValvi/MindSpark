package com.example.mindspark.data.courses.data

import com.example.mindspark.data.courses.model.SpecialOffersModel

/**
 * SpecialOfferData.kt
 *
 * Provides data for special course offers.
 */
class SpecialOfferData {
    val specialOffers = listOf(
        SpecialOffersModel("OFFER1", "20% off on Android courses", "2025-12-31"),
        SpecialOffersModel("OFFER2", "Buy one get one free", "2025-06-30")
    )
}
