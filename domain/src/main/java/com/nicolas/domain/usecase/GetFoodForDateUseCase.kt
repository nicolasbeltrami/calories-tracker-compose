package com.nicolas.domain.usecase

import com.nicolas.domain.model.TrackedFood
import com.nicolas.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

class GetFoodForDateUseCase(
    private val repository: TrackerRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(date)
    }
}