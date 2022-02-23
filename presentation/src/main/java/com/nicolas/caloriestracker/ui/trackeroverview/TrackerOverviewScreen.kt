package com.nicolas.caloriestracker.ui.trackeroverview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.caloriestracker.R
import com.nicolas.caloriestracker.navigation.NavigationEvent
import com.nicolas.caloriestracker.ui.composables.AddButton
import com.nicolas.caloriestracker.ui.composables.DaySelector
import com.nicolas.caloriestracker.ui.composables.ExpandableMealItem
import com.nicolas.caloriestracker.ui.composables.NutrientsHeader
import com.nicolas.caloriestracker.ui.composables.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigate: (NavigationEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(32.dp))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayCLickEvent)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClickEvent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        items(state.meals) { meal ->
            ExpandableMealItem(
                meal = meal,
                onToggleClick = {
                    viewModel.onEvent(
                        TrackerOverviewEvent.OnToggleMealClickEvent(meal)
                    )
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        state.trackedFoods.forEach { food ->
                            TrackedFoodItem(
                                trackedFood = food, onDeleteClick = {
                                    viewModel.onEvent(
                                        TrackerOverviewEvent.OnDeleteTrackedFoodClickEvent(food)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        AddButton(
                            text = stringResource(
                                id = R.string.add_meal,
                                meal.name.asString(context = context)
                            ),
                            onClick = {
                                viewModel.onEvent(
                                    TrackerOverviewEvent.OnAddFoodClickEvent(meal)
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}