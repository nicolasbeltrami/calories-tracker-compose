package com.nicolas.caloriestracker.ui.onboarding.nutrientgoal

sealed class NutrientGoalEvent {
    data class OnCarbRatioEnterEVent(val ratio: String): NutrientGoalEvent()
    data class OnProteinRatioEnterEVent(val ratio: String): NutrientGoalEvent()
    data class OnFatRatioEnterEVent(val ratio: String): NutrientGoalEvent()
    object OnNextClick: NutrientGoalEvent()
}
