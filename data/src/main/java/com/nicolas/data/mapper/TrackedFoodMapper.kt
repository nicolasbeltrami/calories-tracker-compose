package com.nicolas.data.mapper

import com.nicolas.data.local.entity.TrackedFoodEntity
import com.nicolas.domain.model.MealType
import com.nicolas.domain.model.TrackedFood
import org.threeten.bp.LocalDate

class TrackedFoodMapper {
    fun fromTrackedFoodEntityToTrackedFood(trackedFoodEntity: TrackedFoodEntity): TrackedFood {
        return TrackedFood(
            id = trackedFoodEntity.id,
            name = trackedFoodEntity.name,
            carbs = trackedFoodEntity.carbs,
            proteins = trackedFoodEntity.protein,
            calories = trackedFoodEntity.calories,
            imageUrl = trackedFoodEntity.imageUrl,
            fat = trackedFoodEntity.fat,
            mealType = MealType.fromString(trackedFoodEntity.type),
            amount = trackedFoodEntity.amount,
            date = LocalDate.of(
                trackedFoodEntity.year,
                trackedFoodEntity.month,
                trackedFoodEntity.dayOfMont
            )
        )
    }

    fun fromTrackedFoodToTrackedFoodEntity(trackedFood: TrackedFood): TrackedFoodEntity {
        return TrackedFoodEntity(
            id = trackedFood.id,
            name = trackedFood.name,
            carbs = trackedFood.carbs,
            protein = trackedFood.proteins,
            calories = trackedFood.calories,
            imageUrl = trackedFood.imageUrl,
            fat = trackedFood.fat,
            type = trackedFood.mealType.name,
            amount = trackedFood.amount,
            dayOfMont = trackedFood.date.dayOfMonth,
            month = trackedFood.date.monthValue,
            year = trackedFood.date.year
        )
    }
}