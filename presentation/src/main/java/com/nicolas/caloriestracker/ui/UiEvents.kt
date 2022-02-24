package com.nicolas.caloriestracker.ui

import com.nicolas.caloriestracker.utils.UiText

sealed class UiEvents {
    data class ShowSnackBarEvent(val message: UiText) : UiEvents()
    data class ShowSnackBarEventString(val message: String) : UiEvents()
    object Success : UiEvents()
}
