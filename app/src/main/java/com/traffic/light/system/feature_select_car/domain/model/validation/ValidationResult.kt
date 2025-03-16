package com.traffic.light.system.feature_select_car.domain.model.validation

/**
 * Represents the outcome of a validation operation.
 *
 * This sealed class has two possible restricted outcomes:
 * - [Valid]: Indicates that the validation succeeded.
 * - [Invalid]: Indicates that the validation failed, providing an error resource ID.
 */
sealed class ValidationResult {

    /**
     * Indicates that the input is valid.
     */
    data object Valid : ValidationResult()

    /**
     * Indicates that the input is invalid.
     *
     * @property errorResId Resource ID for the error message associated with the invalid input.
     */
    data class Invalid(val errorResId: Int) : ValidationResult()
}