package com.traffic.light.system.feature_select_car.domain.repository

import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import kotlinx.coroutines.flow.Flow

interface SelectCarRepository {
    suspend fun saveCarModel(carModel: String): PreferencesResult
    suspend fun getCarModel(): Flow<String?>
}