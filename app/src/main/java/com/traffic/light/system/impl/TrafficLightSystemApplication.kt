package com.traffic.light.system.impl

import android.app.Application
import com.traffic.light.system.feature_select_car.di.SelectCarModule
import com.traffic.light.system.feature_select_car.di.SelectCarModuleImpl
import com.traffic.light.system.feature_traffic_light.di.TrafficLightModule
import com.traffic.light.system.feature_traffic_light.di.TrafficLightModuleImpl

/**
 * The main Application class for the Traffic Light System.
 *
 * This class serves as the entry point for the application and demonstrates a Clean Architecture
 * approach by separating concerns into distinct packages. It employs manual dependency injection to
 * initialize and expose the following modules:
 *
 * - **selectCarModule**: Handles car selection and preferences saving using DataStore.
 * - **trafficLightModule**: Provides dependencies for managing the traffic light functionality.
 *
 * Both modules are available as singletons via the companion object.
 */
class TrafficLightSystemApplication : Application() {
    companion object {
        lateinit var selectCarModule: SelectCarModule
        lateinit var trafficLightModule: TrafficLightModule
    }

    override fun onCreate() {
        super.onCreate()

        selectCarModule = SelectCarModuleImpl(this@TrafficLightSystemApplication)
        trafficLightModule = TrafficLightModuleImpl()
    }
}