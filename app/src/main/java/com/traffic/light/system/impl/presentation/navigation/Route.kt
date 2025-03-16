package com.traffic.light.system.impl.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Represents a navigation route in the application.
 *
 * This sealed interface provides a type-safe way of navigating between screens, ensuring that only
 * valid routes are used. Each route is either a single object or a parameterized data class, allowing
 * compile-time verification of navigation parameters and simplifies the routing,
 * while avoids the usage of strings as routes and arguments.
 */
sealed interface Route {
    @Serializable
    data object SelectCarModel : Route

    @Serializable
    data class TrafficLight(val carModel: String) : Route
}