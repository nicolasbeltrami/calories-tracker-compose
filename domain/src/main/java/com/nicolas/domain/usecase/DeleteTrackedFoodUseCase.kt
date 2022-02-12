package com.nicolas.domain.usecase

import com.nicolas.domain.model.TrackedFood
import com.nicolas.domain.repository.TrackerRepository

class DeleteTrackedFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(trackedFood: TrackedFood) {
        repository.deleteTrackedFood(trackedFood)
    }
}