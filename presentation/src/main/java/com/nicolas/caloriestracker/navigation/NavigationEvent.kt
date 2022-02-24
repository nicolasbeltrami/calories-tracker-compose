package com.nicolas.caloriestracker.navigation

sealed class NavigationEvent {
    object NavigateUp : NavigationEvent()
}
