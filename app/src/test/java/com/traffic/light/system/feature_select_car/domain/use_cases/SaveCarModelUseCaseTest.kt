package com.traffic.light.system.feature_select_car.domain.use_cases

import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveCarModelUseCaseTest {
    private val mockRepository = mockk<SelectCarRepository>()
    private val useCase = SaveCarModelUseCase(mockRepository)

    @Test
    fun `invoke should call repository with correct parameter and return result`() = runBlocking {
        val carModel = "Audi"
        val expectedResult = PreferencesResult.Success
        coEvery { mockRepository.saveCarModel(carModel) } returns expectedResult

        val result = useCase.invoke(carModel)

        coVerify { mockRepository.saveCarModel(carModel) }
        assertEquals(expectedResult, result)
    }
}