package com.nicolas.caloriestracker.ui.onboarding.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.caloriestracker.R
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.ui.composables.ActionButton
import com.nicolas.caloriestracker.ui.composables.UnitTextField
import com.nicolas.caloriestracker.ui.UiEvents

@Composable
fun AgeScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    viewModel: AgeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.Success -> onNextClick()
                else -> Unit
            }
        }

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.ShowSnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
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
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(24.dp))
            UnitTextField(
                value = viewModel.age,
                onValueChange = viewModel::onEnterAge,
                unit = stringResource(
                    id = R.string.years
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onCLick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}