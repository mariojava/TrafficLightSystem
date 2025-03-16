package com.traffic.light.system.feature_select_car.domain.use_cases

import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository
import kotlinx.coroutines.flow.Flow

class GetCarModelUseCase(private val repository: SelectCarRepository) {
    suspend operator fun invoke(): Flow<String?> {
        return repository.getCarModel()
    }
}