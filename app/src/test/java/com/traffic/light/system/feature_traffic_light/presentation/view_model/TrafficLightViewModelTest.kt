package com.traffic.light.system.feature_traffic_light.presentation.view_model

import com.traffic.light.system.feature_select_car.MainDispatcherRule
import com.traffic.light.system.feature_traffic_light.presentation.model.TrafficLightState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrafficLightViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var viewModel: TrafficLightViewModel =
        TrafficLightViewModel(mainDispatcherRule.testDispatcher)

    @Test
    fun `trafficLightState should start with green`() = runTest {
        assertEquals(TrafficLightState.Green, viewModel.trafficLightState.value)
    }

    @Test
    fun `startLights should transition through all states with correct delays`() = runTest {
        val stateChanges = mutableListOf<TrafficLightState>()

        // Collect values for traffic light state in one loop
        val job = launch {
            viewModel.trafficLightState.take(3).collect { state ->
                stateChanges.add(state)
            }
        }

        launch { viewModel.startLights() }

        advanceTimeBy(TrafficLightViewModel.GREEN_LIGHT_DURATION_MS)
        assertEquals(TrafficLightState.Green, stateChanges[0])

        advanceTimeBy(TrafficLightViewModel.ORANGE_LIGHT_DURATION_MS)
        assertEquals(TrafficLightState.Orange, stateChanges[1])

        advanceTimeBy(TrafficLightViewModel.RED_LIGHT_DURATION_MS)
        assertEquals(TrafficLightState.Red, stateChanges[2])

        job.cancel()
    }

    @Test
    fun `startTrafficLightSystem should raise flag for being started`() = runTest {
        viewModel.startTrafficLightSystem()
        val isTrafficLightSystemStarted = viewModel.trafficLightSystemStarted.value
        assertTrue(isTrafficLightSystemStarted)
        viewModel.stopTrafficLightSystem()
    }

    @Test
    fun `stopTrafficLightSystem should change flag for system being stopped`() = runTest {
        viewModel.startTrafficLightSystem()
        viewModel.stopTrafficLightSystem()
        val isTrafficLightSystemStarted = viewModel.trafficLightSystemStarted.value
        assertFalse(isTrafficLightSystemStarted)
    }
}