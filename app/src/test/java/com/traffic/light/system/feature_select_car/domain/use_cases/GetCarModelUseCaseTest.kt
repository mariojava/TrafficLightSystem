package com.traffic.light.system.feature_select_car.domain.use_cases

import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCarModelUseCaseTest {
    private val mockRepository = mockk<SelectCarRepository>()
    private val getCarModelUseCase = GetCarModelUseCase(mockRepository)

    @Test
    fun `invoke should return car model flow from repository`() = runTest {
        val expectedFlow: Flow<String?> = flowOf("Mercedes")
        coEvery { mockRepository.getCarModel() } returns expectedFlow

        val resultFlow = getCarModelUseCase()

        coVerify { mockRepository.getCarModel() }
        assertEquals(expectedFlow, resultFlow)
    }
}