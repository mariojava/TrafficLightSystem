package com.traffic.light.system.impl.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.traffic.light.system.feature_select_car.presentation.view_model.SelectCarViewModel
import com.traffic.light.system.feature_traffic_light.presentation.view_model.TrafficLightViewModel
import com.traffic.light.system.impl.TrafficLightSystemApplication
import com.traffic.light.system.impl.presentation.navigation.MainNavHost
import com.traffic.light.system.impl.presentation.navigation.Route
import com.traffic.light.system.impl.presentation.ui.theme.TrafficLightSystemTheme

/**
 * The single activity entry point of the application.
 * It leverages the usage of Jetpack Compose and a single navigation component - [MainNavHost],
 * to encapsulate all navigation in the implementation package.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrafficLightSystemTheme {
                // Use DI modules to obtain the necessary components to continue compose composition
                val selectCarModule = TrafficLightSystemApplication.selectCarModule
                val trafficLightModule = TrafficLightSystemApplication.trafficLightModule

                val selectCarViewModelFactory = selectCarModule.selectCarViewModelFactory
                val trafficModuleViewModelFactory = trafficLightModule.trafficLightViewModelFactory

                val selectCarViewModel =
                    ViewModelProvider(
                        this,
                        selectCarViewModelFactory
                    )[SelectCarViewModel::class.java]

                val trafficLightViewModel =
                    ViewModelProvider(
                        this,
                        trafficModuleViewModelFactory
                    )[TrafficLightViewModel::class.java]

                val navController = rememberNavController()

                MainNavHost(
                    navController,
                    Route.SelectCarModel,
                    selectCarViewModel,
                    trafficLightViewModel
                ) {
                    navController.navigate(Route.TrafficLight(it))
                }
            }
        }
    }
}