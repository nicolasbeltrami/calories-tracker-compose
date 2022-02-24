package com.nicolas.caloriestracker.ui.onboarding.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.caloriestracker.R
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.ui.UiEvents
import com.nicolas.caloriestracker.ui.composables.ActionButton
import com.nicolas.caloriestracker.ui.composables.SelectableButton
import com.nicolas.domain.model.GoalType

@Composable
fun GoalScreen(
    onNextClick: () -> Unit,
    viewModel: GoalViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.Success -> onNextClick()
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.your_goal),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.lose),
                    isSelected = viewModel.selectedGoal is GoalType.LoseWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGoalTypeSelected(GoalType.LoseWeight)
                    },
                    textStyle = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.width(16.dp))
                SelectableButton(
                    text = stringResource(id = R.string.keep),
                    isSelected = viewModel.selectedGoal is GoalType.KeepWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGoalTypeSelected(GoalType.KeepWeight)
                    },
                    textStyle = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.width(16.dp))
                SelectableButton(
                    text = stringResource(id = R.string.gain),
                    isSelected = viewModel.selectedGoal is GoalType.GainWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGoalTypeSelected(GoalType.GainWeight)
                    },
                    textStyle = MaterialTheme.typography.button
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onCLick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }


}