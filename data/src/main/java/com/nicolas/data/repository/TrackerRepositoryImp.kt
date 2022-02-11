package com.nicolas.data.repository

import com.nicolas.data.local.TrackerDao
import com.nicolas.data.remote.OpenFoodApi
import com.nicolas.domain.model.TrackableFood
import com.nicolas.domain.model.TrackedFood
import com.nicolas.domain.repository.TrackerRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

class TrackerRepositoryImp(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<TrackableFood> {
        return try {
            val searchResponse = api.searchFood(
                query = query,
                page =  page,
                pageSize = pageSize
            )
            Result.success(searchResponse.products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        TODO("Not yet implemented")
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        TODO("Not yet implemented")
    }
}