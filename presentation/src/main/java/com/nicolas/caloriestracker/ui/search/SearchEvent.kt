package com.nicolas.caloriestracker.ui.search

import com.nicolas.domain.model.MealType
import com.nicolas.domain.model.TrackableFood
import org.threeten.bp.LocalDate

sealed interface SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent
    object OnSearch: SearchEvent
    data class OnToggleTrackableFood(val food: TrackableFood): SearchEvent
    data class OnFoodAmountChange(
        val food: TrackableFood,
        val amount: String
    ): SearchEvent
    data class OnTrackFoodCLick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ): SearchEvent
    data class OnSearchFocusChange(val isFocused: Boolean): SearchEvent
}
