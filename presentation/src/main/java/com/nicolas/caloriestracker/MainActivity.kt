package com.nicolas.caloriestracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nicolas.caloriestracker.navigation.Route
import com.nicolas.caloriestracker.ui.onboarding.WelcomeScreen
import com.nicolas.caloriestracker.ui.onboarding.activitylevel.ActivityLevelScreen
import com.nicolas.caloriestracker.ui.onboarding.age.AgeScreen
import com.nicolas.caloriestracker.ui.onboarding.gender.GenderScreen
import com.nicolas.caloriestracker.ui.onboarding.goal.GoalScreen
import com.nicolas.caloriestracker.ui.onboarding.height.HeightScreen
import com.nicolas.caloriestracker.ui.onboarding.nutrientgoal.NutrientGoalScreen
import com.nicolas.caloriestracker.ui.onboarding.weight.WeightScreen
import com.nicolas.caloriestracker.ui.search.SearchScreen
import com.nicolas.caloriestracker.ui.theme.CaloriesTrackerTheme
import com.nicolas.caloriestracker.ui.trackeroverview.TrackerOverviewScreen
import com.nicolas.domain.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        val shouldShowOnBoarding = preferences.loadShouldShowOnboarding()
        setContent {
            CaloriesTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnBoarding) {
                            Route.WELCOME
                        } else {
                            Route.TRACKER_OVERVIEW
                        }
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(
                                onNextClick = {
                                    navController.navigate(Route.GENDER)
                                }
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(
                                onNextClick = {
                                    navController.navigate(Route.AGE)
                                })
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.HEIGHT)
                                }
                            )
                        }

                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                }
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                }
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityLevelScreen(
                                onNextClick = {
                                    navController.navigate(Route.GOAL)
                                }
                            )
                        }
                        composable(Route.GOAL) {
                            GoalScreen(
                                onNextClick = {
                                    navController.navigate(Route.NUTRIENT_GOAL)
                                }
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                }
                            )
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigate = { mealName, day, month, year ->
                                    navController.navigate(
                                        Route.SEARCH + "/$mealName" +
                                                "/$day" +
                                                "/$month" +
                                                "/$year"
                                    )
                                }
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMont = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = { navController.navigateUp() })
                        }
                    }
                }
            }
        }
    }
}