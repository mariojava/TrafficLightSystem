package com.traffic.light.system.feature_select_car.presentation.view_model


import com.traffic.light.system.core.MainDispatcherRule
import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import com.traffic.light.system.feature_select_car.domain.model.validation.ValidationResult
import com.traffic.light.system.feature_select_car.domain.use_cases.SelectCarUseCases
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SelectCarViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockUseCases = mockk<SelectCarUseCases>()
    private val viewModel = SelectCarViewModel(mainDispatcherRule.testDispatcher, mockUseCases)

    @Test
    fun `onChangeCardModel should update carModelState`() {
        val carModel = "Opel"

        viewModel.onChangeCardModel(carModel)

        assertEquals(carModel, viewModel.carModelState.value)
    }

    @Test
    fun `getInitialCarModel should fetch and update car model`() = runTest {
        val initialCarModel = "Mazda"
        coEvery { mockUseCases.getCarModelUseCase() } returns flowOf(initialCarModel)

        viewModel.getInitialCarModel()
        advanceUntilIdle()

        assertEquals(initialCarModel, viewModel.carModelState.value)
    }

    @Test
    fun `processCarModel should set error message if validation fails`() = runTest {
        val invalidCarModel = "Fiat"
        val validationResult = ValidationResult.Invalid(errorResId = 1234)
        coEvery { mockUseCases.validateCarModelUseCase(invalidCarModel) } returns validationResult

        viewModel.processCarModel(invalidCarModel)

        assertEquals(1234, viewModel.carModelErrorMessageRes.value)
        assertEquals(false, viewModel.isProcessingSelectedCarInfo.value)
    }

    @Test
    fun `processCarModel should save car model if validation passes`() = runTest {
        val validCarModel = "Opel"
        val validationResult = ValidationResult.Valid
        coEvery { mockUseCases.validateCarModelUseCase(validCarModel) } returns validationResult
        coEvery { mockUseCases.saveCarModelUseCase(validCarModel) } returns PreferencesResult.Success

        viewModel.processCarModel(validCarModel)
        advanceUntilIdle()

        assertEquals(0, viewModel.carModelErrorMessageRes.value)
        coVerify { mockUseCases.saveCarModelUseCase(validCarModel) }
    }

    @Test
    fun `saveCarModel should emit return message if saving fails`() = runTest {
        val carModel = "Opel"
        val exceptionMessage = "Error while saving"
        coEvery { mockUseCases.saveCarModelUseCase(carModel) } returns PreferencesResult.Error(
            Exception(exceptionMessage)
        )

        viewModel.saveCarModel(carModel)
        advanceUntilIdle()

        val errorMessage = viewModel.saveCarModelErrorMessage.first()
        assertEquals(exceptionMessage, errorMessage)
    }

    @Test
    fun `saveCarModel should return true if saving succeeds`() = runTest {
        val carModel = "Mercedes"
        coEvery { mockUseCases.saveCarModelUseCase(carModel) } returns PreferencesResult.Success

        viewModel.saveCarModel(carModel)
        advanceUntilIdle()

        val isCompleted = viewModel.isCarSelectionCompleted.first()
        assertEquals(true, isCompleted)
    }
}