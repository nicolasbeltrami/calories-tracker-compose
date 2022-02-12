package com.nicolas.caloriestracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nicolas.caloriestracker.ui.onboarding.WelcomeScreen
import com.nicolas.caloriestracker.ui.theme.CaloriesTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriesTrackerTheme {
                WelcomeScreen()
            }
        }
    }
}