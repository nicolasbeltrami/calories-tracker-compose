package com.nicolas.caloriestracker.ui.trackeroverview

import com.nicolas.caloriestracker.ui.trackeroverview.model.MealState
import com.nicolas.caloriestracker.ui.trackeroverview.model.defaultMeals
import com.nicolas.domain.model.TrackedFood
import org.threeten.bp.LocalDate

data class TrackerOverViewState(
    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbsGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val caloriesGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val trackedFoods: List<TrackedFood> = listOf(),
    val meals: List<MealState> = defaultMeals
)
