package com.traffic.light.system.feature_traffic_light.presentation.model

sealed class TrafficLightState {
    data object Red : TrafficLightState()
    data object Green : TrafficLightState()
    data object Orange : TrafficLightState()
}