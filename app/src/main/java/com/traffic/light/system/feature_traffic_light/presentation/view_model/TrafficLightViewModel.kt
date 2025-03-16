package com.traffic.light.system.feature_traffic_light.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.light.system.feature_traffic_light.presentation.model.TrafficLightState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrafficLightViewModel(private val dispatcher: CoroutineDispatcher) : ViewModel() {
    companion object {
        const val RED_LIGHT_DURATION_MS = 4000L
        const val GREEN_LIGHT_DURATION_MS = 4000L
        const val ORANGE_LIGHT_DURATION_MS = 1000L
    }

    private val _trafficLightState = MutableStateFlow<TrafficLightState>(TrafficLightState.Green)
    val trafficLightState: StateFlow<TrafficLightState> = _trafficLightState.asStateFlow()

    private val _trafficLightSystemStarted = MutableStateFlow(false)
    val trafficLightSystemStarted: StateFlow<Boolean> = _trafficLightSystemStarted.asStateFlow()

    private lateinit var trafficLightSystemJob: Job

    fun startTrafficLightSystem() {
        // Indicate the start of the system to prevent starting again upon configuration change.
        _trafficLightSystemStarted.value = true

        trafficLightSystemJob = viewModelScope.launch(dispatcher) {
            while (true) {
                startLights()
            }
        }
    }

    suspend fun startLights() {
        _trafficLightState.value = TrafficLightState.Green
        delay(GREEN_LIGHT_DURATION_MS)
        _trafficLightState.value = TrafficLightState.Orange
        delay(ORANGE_LIGHT_DURATION_MS)
        _trafficLightState.value = TrafficLightState.Red
        delay(RED_LIGHT_DURATION_MS)
    }

    fun stopTrafficLightSystem() {
        trafficLightSystemJob.cancel()
        _trafficLightSystemStarted.value = false
    }
}