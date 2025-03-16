package com.traffic.light.system.feature_select_car.domain.use_cases

import com.traffic.light.system.R
import com.traffic.light.system.feature_select_car.domain.model.validation.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class ValidateCarModelUseCaseTest {
    private lateinit var validateCarModelUseCase: ValidateCarModelUseCase

    @Before
    fun setUp() {
        validateCarModelUseCase = ValidateCarModelUseCase()
    }

    @Test
    fun `invoke returns invalid when car model is empty`() {
        val input = ""
        when (val result = validateCarModelUseCase(input)) {
            is ValidationResult.Invalid -> {
                assertEquals(R.string.empty_card_model_error, result.errorResId)
            }

            else -> fail("Expected ValidationResult.Invalid for empty input")
        }
    }

    @Test
    fun `invoke returns invalid when car model is too short`() {
        val input = "ab"
        when (val result = validateCarModelUseCase(input)) {
            is ValidationResult.Invalid -> {
                assertEquals(R.string.invalid_car_model_error, result.errorResId)
            }

            else -> fail("Expected ValidationResult.Invalid for input 'ab'")
        }
    }

    @Test
    fun `invoke returns valid when car model is valid`() {
        val input = "Audi"
        val result = validateCarModelUseCase(input)

        assertEquals(ValidationResult.Valid, result)
    }
}