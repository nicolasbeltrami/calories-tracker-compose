package com.nicolas.data.repository

import com.nicolas.data.local.TrackerDao
import com.nicolas.data.mapper.TrackableFoodMapper
import com.nicolas.data.mapper.TrackedFoodMapper
import com.nicolas.data.remote.OpenFoodApi
import com.nicolas.data.remote.model.Product
import com.nicolas.domain.model.TrackableFood
import com.nicolas.domain.model.TrackedFood
import com.nicolas.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate

class TrackerRepositoryImp(
    private val dao: TrackerDao,
    private val api: OpenFoodApi,
    private val trackableFoodMapper: TrackableFoodMapper,
    private val trackedFoodMapper: TrackedFoodMapper
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchResponse = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            val trackableProduct = searchResponse.products.map { product: Product -> trackableFoodMapper.fromProductToTrackableFood(product) }
            Result.success(trackableProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        val foodEntity = trackedFoodMapper.fromTrackedFoodToTrackedFoodEntity(food)
        dao.insertTrackedFood(foodEntity)
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        val foodEntity = trackedFoodMapper.fromTrackedFoodToTrackedFoodEntity(food)
        dao.deleteTrackedFood(foodEntity)
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
                entities.map {
                    trackedFoodMapper.fromTrackedFoodEntityToTrackedFood(it)
                }
        }
    }
}