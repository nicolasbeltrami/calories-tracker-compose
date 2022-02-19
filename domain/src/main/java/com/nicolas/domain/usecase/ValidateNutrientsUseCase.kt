package com.nicolas.domain.usecase

class ValidateNutrientsUseCase {

    operator fun invoke(
        carbsRatioValue: String,
        proteinRatioValue: String,
        fatRatioValue: String
    ) : Result{
        val carbsRatio = carbsRatioValue.toIntOrNull()
        val proteinRatio = proteinRatioValue.toIntOrNull()
        val fatRatio = fatRatioValue.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(
                message = "Error"
            )
        }
        if (carbsRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(
                message = "Error percentage"
            )
        }
        return Result.Success(
            carbsRatio / 100f,
            proteinRatio / 100f,
            fatRatio / 100f)
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float,
        ) : Result()
        data class Error(val message: String): Result()
    }

}