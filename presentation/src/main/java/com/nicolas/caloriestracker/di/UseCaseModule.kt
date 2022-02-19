package com.nicolas.caloriestracker.di

import com.nicolas.domain.usecase.ValidateNutrientsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateNutrientsUseCase(): ValidateNutrientsUseCase {
        return ValidateNutrientsUseCase()
    }

}