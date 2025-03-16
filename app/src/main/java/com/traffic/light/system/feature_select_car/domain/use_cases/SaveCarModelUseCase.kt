package com.traffic.light.system.feature_select_car.domain.use_cases

import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository

class SaveCarModelUseCase(private val repository: SelectCarRepository) {
    suspend operator fun invoke(carModel: String): PreferencesResult {
        return repository.saveCarModel(carModel)
    }
}