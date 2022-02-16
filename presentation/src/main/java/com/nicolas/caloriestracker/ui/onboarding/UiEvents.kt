package com.nicolas.caloriestracker.ui.onboarding

import com.nicolas.caloriestracker.utils.UiText

sealed class UiEvents {
    data class ShowSnackBarEvent(val message: UiText) : UiEvents()
}
