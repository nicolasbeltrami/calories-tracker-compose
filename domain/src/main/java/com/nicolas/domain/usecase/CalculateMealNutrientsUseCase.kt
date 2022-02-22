package com.nicolas.domain.usecase

import com.nicolas.domain.model.ActivityLevel
import com.nicolas.domain.model.Gender
import com.nicolas.domain.model.GoalType
import com.nicolas.domain.model.MealType
import com.nicolas.domain.model.TrackedFood
import com.nicolas.domain.model.UserInfo
import com.nicolas.domain.preferences.Preferences
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
    private val preferences: Preferences
) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val foods = entry.value
                MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    proteins = foods.sumOf { it.proteins },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProteins = allNutrients.values.sumOf { it.proteins }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val calorieGoal = dailyCaloriesRequirement(userInfo)
        val carbsGoal = (calorieGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinsGoal = (calorieGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (calorieGoal * userInfo.fatRatio / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinsGoal = proteinsGoal,
            fatGoal = fatGoal,
            caloriesGoal = calorieGoal,
            totalCarbs = totalCarbs,
            totalProteins = totalProteins,
            totalFatGoal = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )

    }

    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age
                        ).roundToInt()
            }
            is Gender.Female -> {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67f * userInfo.age
                        ).roundToInt()
            }
        }
    }

    private fun dailyCaloriesRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            ActivityLevel.High -> 1.4f
            ActivityLevel.Low -> 1.2f
            ActivityLevel.Medium -> 1.3f
        }

        val calorieExtra = when (userInfo.goalType) {
            GoalType.GainWeight -> 500
            GoalType.KeepWeight -> 0
            GoalType.LoseWeight -> -500
        }
        return (bmr(userInfo) * activityFactor + calorieExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val proteins: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinsGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProteins: Int,
        val totalFatGoal: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}
