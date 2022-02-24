package com.nicolas.caloriestracker.ui.trackeroverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.navigation.Route
import com.nicolas.caloriestracker.ui.UiEvents
import com.nicolas.domain.preferences.Preferences
import com.nicolas.domain.usecase.CalculateMealNutrientsUseCase
import com.nicolas.domain.usecase.DeleteTrackedFoodUseCase
import com.nicolas.domain.usecase.GetFoodForDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val preferences: Preferences,
    private val deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase,
    private val getFoodForDateUseCase: GetFoodForDateUseCase,
    private val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase
) : ViewModel() {

    var state by mutableStateOf(TrackerOverViewState())
        private set

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var getFoodForDateJob: Job? = null

    init {
        refreshFoods()
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.OnDeleteTrackedFoodClickEvent -> {
                viewModelScope.launch {
                    deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }
            TrackerOverviewEvent.OnNextDayClickEvent -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
            TrackerOverviewEvent.OnPreviousDayCLickEvent -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverviewEvent.OnToggleMealClickEvent -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = it.isExpanded.not())
                        } else it
                    }
                )
            }
        }
    }

    private fun refreshFoods() {
        getFoodForDateJob?.cancel()
        getFoodForDateJob = getFoodForDateUseCase(state.date).onEach { foods ->
            val nutrientsResult = calculateMealNutrientsUseCase(foods)
            state = state.copy(
                totalCarbs = nutrientsResult.totalCarbs,
                totalProtein = nutrientsResult.totalProteins,
                totalFat = nutrientsResult.totalFat,
                totalCalories = nutrientsResult.totalCalories,
                proteinGoal = nutrientsResult.proteinsGoal,
                fatGoal = nutrientsResult.fatGoal,
                carbsGoal = nutrientsResult.carbsGoal,
                caloriesGoal = nutrientsResult.caloriesGoal,
                trackedFoods = foods,
                meals = state.meals.map {
                    val nutrientsForMeal = nutrientsResult.mealNutrients[it.mealType]
                        ?: return@map it.copy(
                            carbs = 0,
                            protein = 0,
                            fat = 0,
                            calories = 0
                        )
                    it.copy(
                        carbs = nutrientsForMeal.carbs,
                        protein = nutrientsForMeal.proteins,
                        fat = nutrientsForMeal.fat,
                        calories = nutrientsForMeal.calories
                    )
                }
            )

        }.launchIn(viewModelScope)
    }
}