package com.nicolas.domain.usecase

import com.nicolas.domain.model.TrackableFood
import com.nicolas.domain.repository.TrackerRepository

class SearchFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>> {
        if (query.isNullOrBlank()) {
            return Result.success(listOf())
        }
        return repository.searchFood(query.trim(), page, pageSize)
    }
}