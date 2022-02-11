package com.nicolas.data.mapper

import com.nicolas.data.remote.model.Product
import com.nicolas.domain.model.TrackableFood
import kotlin.math.roundToInt

class TrackableFoodMapper () {
    fun fromProductToTrackableFood(product: Product): TrackableFood {
        return TrackableFood(
            name = product.productName,
            imageUrl = product.imageFrontThumbUrl,
            caloriesPer100g = product.nutriments.energyKcal100g.roundToInt(),
            carbsPer100g = product.nutriments.carbohydrates100g.roundToInt(),
            proteinPer100g = product.nutriments.proteins100g.roundToInt(),
            fatPer100g = product.nutriments.fat100g.roundToInt()
        )
    }
}