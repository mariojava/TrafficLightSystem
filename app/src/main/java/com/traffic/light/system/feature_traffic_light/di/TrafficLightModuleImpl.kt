package com.traffic.light.system.feature_traffic_light.di

import androidx.lifecycle.ViewModelProvider
import com.traffic.light.system.core.presentation.factory.viewModelFactory
import com.traffic.light.system.feature_traffic_light.presentation.view_model.TrafficLightViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Implementation of the [TrafficLightModule] interface for the Traffic Light feature.
 *
 * This module is responsible for providing the necessary dependencies for the Traffic Light
 * functionality, such as creating the [TrafficLightViewModel] with the proper coroutine context.
 *
 */
class TrafficLightModuleImpl : TrafficLightModule {

    /**
     * The default [CoroutineDispatcher] used by the Traffic Light feature
     */
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    /**
     * A [ViewModelProvider.Factory] for creating instances of [TrafficLightViewModel].
     *
     * The factory leverages the [viewModelFactory] extension to construct the view model
     */
    override val trafficLightViewModelFactory: ViewModelProvider.Factory by lazy {
        viewModelFactory { TrafficLightViewModel(defaultDispatcher) }
    }
}