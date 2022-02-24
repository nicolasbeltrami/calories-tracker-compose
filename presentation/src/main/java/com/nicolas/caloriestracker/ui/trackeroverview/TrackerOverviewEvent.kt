package com.nicolas.caloriestracker.ui.trackeroverview

import com.nicolas.caloriestracker.ui.trackeroverview.model.MealState
import com.nicolas.domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object OnNextDayClickEvent : TrackerOverviewEvent()
    object OnPreviousDayCLickEvent : TrackerOverviewEvent()
    data class OnToggleMealClickEvent(val meal: MealState) : TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClickEvent(val trackedFood: TrackedFood) : TrackerOverviewEvent()
}
