package com.traffic.light.system.feature_select_car.domain.use_cases

import com.traffic.light.system.R
import com.traffic.light.system.feature_select_car.domain.model.validation.ValidationResult

class ValidateCarModelUseCase {
    companion object {
        const val CAR_MODEL_MIN_LENGTH = 3
    }

    operator fun invoke(carModel: String): ValidationResult {
        return when {
            carModel.isEmpty() -> ValidationResult.Invalid(R.string.empty_card_model_error)
            carModel.length < CAR_MODEL_MIN_LENGTH -> ValidationResult.Invalid(R.string.invalid_car_model_error)
            else -> ValidationResult.Valid
        }
    }
}