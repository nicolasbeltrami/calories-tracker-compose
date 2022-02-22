package com.nicolas.caloriestracker.di

import com.nicolas.domain.preferences.Preferences
import com.nicolas.domain.repository.TrackerRepository
import com.nicolas.domain.usecase.CalculateMealNutrientsUseCase
import com.nicolas.domain.usecase.DeleteTrackedFoodUseCase
import com.nicolas.domain.usecase.GetFoodForDateUseCase
import com.nicolas.domain.usecase.SearchFoodUseCase
import com.nicolas.domain.usecase.TrackFoodUseCase
import com.nicolas.domain.usecase.ValidateNutrientsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNutrientsUseCase(): ValidateNutrientsUseCase {
        return ValidateNutrientsUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideCalculateMealNutrients(preferences: Preferences): CalculateMealNutrientsUseCase {
        return CalculateMealNutrientsUseCase(preferences = preferences)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTrackedFoodUseCase(repository: TrackerRepository): DeleteTrackedFoodUseCase {
        return DeleteTrackedFoodUseCase(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFoodForDateUseCase(repository: TrackerRepository): GetFoodForDateUseCase {
        return GetFoodForDateUseCase(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchFoodUseCase(repository: TrackerRepository): SearchFoodUseCase {
        return SearchFoodUseCase(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideTrackFoodUseCase(repository: TrackerRepository): TrackFoodUseCase {
        return TrackFoodUseCase(repository = repository)
    }
}
