package com.traffic.light.system.feature_select_car.domain.use_cases

data class SelectCarUseCases(
    val saveCarModelUseCase: SaveCarModelUseCase,
    val getCarModelUseCase: GetCarModelUseCase,
    val validateCarModelUseCase: ValidateCarModelUseCase
)