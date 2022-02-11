package com.nicolas.domain.model

import java.time.LocalDate

data class TrackedFood(
    val id: Int? = null,
    val name: String,
    val carbs: Int,
    val proteins: Int,
    val fat: Int,
    val calories: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val amount: Int,
    val date: LocalDate,
)
