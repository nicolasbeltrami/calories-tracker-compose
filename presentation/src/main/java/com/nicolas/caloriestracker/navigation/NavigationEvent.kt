package com.nicolas.caloriestracker.navigation

sealed class NavigationEvent {
    data class Navigate(val route: String) : NavigationEvent()
    object NavigateUp : NavigationEvent()
}
