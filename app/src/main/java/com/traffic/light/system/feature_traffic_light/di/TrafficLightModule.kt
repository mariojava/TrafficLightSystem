package com.traffic.light.system.feature_traffic_light.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository
import com.traffic.light.system.feature_select_car.domain.use_cases.SelectCarUseCases

interface TrafficLightModule {
    val trafficLightViewModelFactory: ViewModelProvider.Factory
}