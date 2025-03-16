package com.traffic.light.system.feature_select_car.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import com.traffic.light.system.feature_select_car.domain.model.validation.ValidationResult
import com.traffic.light.system.feature_select_car.domain.use_cases.SelectCarUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SelectCarViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val selectCarUseCases: SelectCarUseCases
) :
    ViewModel() {
    private val _carModelState = MutableStateFlow("")
    val carModelState = _carModelState.asStateFlow()

    private val _carModelErrorMessageRes = MutableStateFlow(0)
    val carModelErrorMessageRes = _carModelErrorMessageRes.asStateFlow()

    private val _saveCarModelErrorMessage = Channel<String>(Channel.BUFFERED)
    val saveCarModelErrorMessage = _saveCarModelErrorMessage.receiveAsFlow()

    private val _isProcessingSelectedCarInfo = MutableStateFlow(false)
    var isProcessingSelectedCarInfo = _isProcessingSelectedCarInfo.asStateFlow()

    private val _isCarSelectionCompleted = Channel<Boolean>(Channel.BUFFERED)
    val isCarSelectionCompleted = _isCarSelectionCompleted.receiveAsFlow()

    private val _isLoadingInitialCarModel = MutableStateFlow(false)
    val isLoadingInitialCarModel = _isLoadingInitialCarModel
        .onStart { getInitialCarModel() } // obtain initial car model when collecting this state starts
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    fun onChangeCardModel(carModel: String) {
        _carModelState.value = carModel
    }

    fun getInitialCarModel() {
        viewModelScope.launch(dispatcher) {
            _isLoadingInitialCarModel.value = true
            selectCarUseCases.getCarModelUseCase().collect { carModel ->
                _carModelState.value = carModel ?: ""
                _isLoadingInitialCarModel.value = false
            }
        }
    }

    fun processCarModel(carModel: String) {
        _isProcessingSelectedCarInfo.value = true

        // Validate User input first, before you continue onwards with saving
        val result = selectCarUseCases.validateCarModelUseCase(carModel)

        if (result is ValidationResult.Invalid) {
            _carModelErrorMessageRes.value = result.errorResId
            _isProcessingSelectedCarInfo.value = false
        } else {
            _carModelErrorMessageRes.value = 0
            // Save car model locally after successful validation
            saveCarModel(carModel)
        }
    }

    fun saveCarModel(carModel: String) {
        viewModelScope.launch(dispatcher) {
            val result = selectCarUseCases.saveCarModelUseCase(carModel)
            _isProcessingSelectedCarInfo.value = false

            //
            if (result is PreferencesResult.Error) {
                _saveCarModelErrorMessage.send(result.exception.message ?: "")
            } else {
                _isCarSelectionCompleted.send(true)
            }
        }
    }
}