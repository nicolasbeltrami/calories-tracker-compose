package com.nicolas.caloriestracker.ui.onboarding

import com.nicolas.caloriestracker.utils.UiText

sealed class UiEvents {
    data class ShowSnackBarEvent(val message: UiText) : UiEvents()
    data class ShowSnackBarEventString(val message: String) : UiEvents()
}
