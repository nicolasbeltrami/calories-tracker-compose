package com.nicolas.caloriestracker.navigation

import androidx.navigation.NavController

fun NavController.navigate(event: NavigationEvent.Navigate) {
    this.navigate(event.route)
}