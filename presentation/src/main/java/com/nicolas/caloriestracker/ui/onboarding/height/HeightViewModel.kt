package com.nicolas.caloriestracker.ui.onboarding.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.caloriestracker.R
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.navigation.Route
import com.nicolas.caloriestracker.ui.onboarding.UiEvents
import com.nicolas.caloriestracker.utils.UiText
import com.nicolas.data.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var height by mutableStateOf("0")
        private set

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEnterHeight(height: String) {
        if (height.length <= 3) {
            this.height = height.filter { it.isDigit() }

        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvents.ShowSnackBarEvent(
                        UiText.StringResource(R.string.error_height_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveHeight(heightNumber)
            _navigationEvent.send(NavigationEvent.Navigate(Route.WEIGHT))
        }
    }

}