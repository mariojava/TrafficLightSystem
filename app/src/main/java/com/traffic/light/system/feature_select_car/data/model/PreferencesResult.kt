package com.traffic.light.system.feature_select_car.data.model

/**
 * Represents the result of a preferences operation.
 *
 * This sealed class abstracts the outcome of any operation that deals with user
 * preferences. It can be one of two types:
 *
 * - [PreferencesResult.Success]: Denotes that the operation was successful.
 * - [PreferencesResult.Error]: Denotes that the operation failed, carrying an
 *   associated [Throwable] exception that provides details on the error.
 *
 * Example usage:
 *
 * ```
 * when(val result = saveCarModel()) {
 *     PreferencesResult.Success -> {
 *         // Operation succeeded
 *     }
 *     is PreferencesResult.Error -> {
 *         // Handle the error from result object - ${result.exception.message}
 *     }
 * }
 * ```
 */
sealed class PreferencesResult {
    /**
     * Indicates that the preferences operation completed successfully
     */
    data object Success : PreferencesResult()

    /**
     * Indicates that the preferences operation failed.
     *
     * @property exception the [Throwable] describing the error that occurred.
     */
    data class Error(val exception: Throwable) : PreferencesResult()
}