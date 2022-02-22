package com.nicolas.caloriestracker.ui.onboarding.nutrientgoal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.navigation.Route
import com.nicolas.caloriestracker.ui.onboarding.UiEvents
import com.nicolas.caloriestracker.ui.onboarding.nutrientgoal.NutrientGoalEvent.*
import com.nicolas.domain.preferences.Preferences
import com.nicolas.domain.usecase.ValidateNutrientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is OnCarbRatioEnterEVent -> {
                state = state.copy(
                    carbsRatio = filterDigits(event.ratio)
                )
            }
            is OnFatRatioEnterEVent -> {
                state = state.copy(
                    fatRatio = filterDigits(event.ratio)
                )
            }
            OnNextClick -> {
                val result = validateNutrientsUseCase(
                    carbsRatioValue = state.carbsRatio,
                    proteinRatioValue = state.proteinRatio,
                    fatRatioValue = state.fatRatio
                )

                when(result) {
                    is ValidateNutrientsUseCase.Result.Success -> {
                        preferences.saveCarbsRatio(result.carbsRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _navigationEvent.send(NavigationEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }
                    }
                    is ValidateNutrientsUseCase.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvents.ShowSnackBarEventString(result.message))
                        }
                    }
                }
            }
            is OnProteinRatioEnterEVent -> {
                state = state.copy(
                    proteinRatio = filterDigits(event.ratio)
                )
            }
        }
    }

    private fun filterDigits(value: String): String {
        return value.filter { it.isDigit() }
    }
}