package com.nicolas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedFoodEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: String,
    val calories: Int,
    val imageUrl: String?,
    val type: String,
    val amount: Int,
    val dayOfMont: Int,
    val month: Int,
    val year: Int,
)
