package com.nicolas.caloriestracker.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.caloriestracker.R
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.ui.UiEvents
import com.nicolas.caloriestracker.utils.UiText
import com.nicolas.domain.usecase.SearchFoodUseCase
import com.nicolas.domain.usecase.TrackFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackFoodUseCase: TrackFoodUseCase,
    private val searchFoodUseCase: SearchFoodUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnFoodAmountChange -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food) {
                            it.copy(amount = filterDigits(event.amount))
                        } else {
                            it
                        }
                    }
                )
            }
            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }
            SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = event.isFocused.not() && state.query.isBlank()
                )
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food) {
                            it.copy(isExpanded = it.isExpanded.not())
                        } else {
                            it
                        }
                    }
                )
            }
            is SearchEvent.OnTrackFoodCLick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFood = listOf()
            )
            searchFoodUseCase.invoke(
                state.query
            ).onSuccess { foods ->
                state= state.copy(
                    trackableFood = foods.map {
                        TrackableFoodUiState(it)
                    },
                    isSearching = false,
                    query = ""
                )
            }.onFailure {
                state = state.copy(isSearching = false)
                _uiEvent.send(
                    UiEvents.ShowSnackBarEvent(
                        UiText.StringResource(R.string.error_something_went_wrong)
                    )
                )
            }
        }
    }

    private fun filterDigits(value: String): String {
        return value.filter { it.isDigit() }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodCLick) {
        viewModelScope.launch {
            val uiState = state.trackableFood.find { it.food == event.food }
            trackFoodUseCase.invoke(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _navigationEvent.send(NavigationEvent.NavigateUp)
        }
    }
}