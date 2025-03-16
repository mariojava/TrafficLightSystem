package com.traffic.light.system.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.traffic.light.system.feature_select_car.presentation.composables.SelectCarForm
import com.traffic.light.system.feature_select_car.presentation.view_model.SelectCarViewModel
import com.traffic.light.system.feature_traffic_light.presentation.composables.TrafficLightForm
import com.traffic.light.system.feature_traffic_light.presentation.view_model.TrafficLightViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: Route,
    selectCarViewModel: SelectCarViewModel,
    trafficLightViewModel: TrafficLightViewModel,
    onSelectCarModelCompleted: (String) -> Unit
) {
    NavHost(navController, startDestination) {
        composable<Route.SelectCarModel> {
            // Collect view model states in respect to the lifecycle of the parent and convert to compose states
            val isLoadingInitialCarModel =
                selectCarViewModel.isLoadingInitialCarModel.collectAsStateWithLifecycle()
            val isProcessingSelectedCarInfo =
                selectCarViewModel.isProcessingSelectedCarInfo.collectAsStateWithLifecycle()
            val carModelState = selectCarViewModel.carModelState.collectAsStateWithLifecycle()
            val errorResource =
                selectCarViewModel.carModelErrorMessageRes.collectAsStateWithLifecycle()
            val saveCarError =
                selectCarViewModel.saveCarModelErrorMessage.collectAsStateWithLifecycle("")

            SelectCarForm(
                carModelState.value,
                isLoadingInitialCarModel.value,
                isProcessingSelectedCarInfo.value,
                errorResource.value,
                saveCarError.value,
                { selectCarViewModel.onChangeCardModel(it) },
                { selectCarViewModel.processCarModel(it) }
            )

            LaunchedEffect(true) {
                selectCarViewModel.isCarSelectionCompleted.collect { isCompleted ->
                    if (isCompleted) {
                        onSelectCarModelCompleted(carModelState.value)
                    }
                }
            }
        }
        composable<Route.TrafficLight> {
            val arguments = it.toRoute<Route.TrafficLight>()
            val trafficLightState =
                trafficLightViewModel.trafficLightState.collectAsStateWithLifecycle()
            val trafficLightSystemStarted =
                trafficLightViewModel.trafficLightSystemStarted.collectAsStateWithLifecycle()

            if (!trafficLightSystemStarted.value) {
                LaunchedEffect(true) {
                    trafficLightViewModel.startTrafficLightSystem()
                }
            }

            TrafficLightForm(arguments.carModel, trafficLightState.value)
        }
    }
}