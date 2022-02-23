package com.nicolas.caloriestracker.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.nicolas.caloriestracker.ui.theme.CarbColor
import com.nicolas.caloriestracker.ui.theme.FatColor
import com.nicolas.caloriestracker.ui.theme.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    caloriesGoal: Int,
    modifier: Modifier = Modifier
) {
    val backGround = MaterialTheme.colors.background
    val caloriesExceededColor = MaterialTheme.colors.error
    val carbWidthRatio = remember { Animatable(0f) }
    val proteinWidthRatio = remember { Animatable(0f) }
    val fatWidthRatio = remember { Animatable(0f) }

    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(
            targetValue = ((carbs * 4f) / caloriesGoal)
        )
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(
            targetValue = ((protein * 4f) / caloriesGoal)
        )
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(
            targetValue = ((fat * 9f) / caloriesGoal)
        )
    }

    Canvas(modifier = modifier) {
        if (calories <= caloriesGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinsWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawRoundRect(
                color = backGround,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = FatColor,
                size = Size(
                    width = carbsWidth + proteinsWidth + fatWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = ProteinColor,
                size = Size(
                    width = carbsWidth + proteinsWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = CarbColor,
                size = Size(
                    width = carbsWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            drawRoundRect(
                color = caloriesExceededColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}