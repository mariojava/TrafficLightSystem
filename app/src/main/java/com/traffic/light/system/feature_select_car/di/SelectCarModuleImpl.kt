package com.traffic.light.system.feature_select_car.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.traffic.light.system.core.presentation.factory.viewModelFactory
import com.traffic.light.system.feature_select_car.data.source.local.SelectCarRepositoryImpl
import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository
import com.traffic.light.system.feature_select_car.domain.use_cases.GetCarModelUseCase
import com.traffic.light.system.feature_select_car.domain.use_cases.SaveCarModelUseCase
import com.traffic.light.system.feature_select_car.domain.use_cases.SelectCarUseCases
import com.traffic.light.system.feature_select_car.domain.use_cases.ValidateCarModelUseCase
import com.traffic.light.system.feature_select_car.presentation.view_model.SelectCarViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides the dependency injection implementation for the Select Car feature.
 *
 * This module wires up the necessary dependencies required for the Select Car feature,
 * offering a central place to obtain configured instances of the following:
 *
 * - [DataStore]<[Preferences]>: A DataStore instance backed by Android preferences,
 *   configured with the name "select_car_prefs", used for managing select car preferences.
 * - [SelectCarRepository]: An implementation of the repository interface backed by the
 *   DataStore for saving and retrieving the car model data.
 * - [SelectCarUseCases]: A collection of use cases required by the feature such as saving,
 *   retrieving, and validating a car model.
 * - [ViewModelProvider.Factory]: A factory to create instances of [SelectCarViewModel],
 *   which is used in the UI for interacting with the business logic.
 *
 * @property appContext The application context which is used to initialize the DataStore.
 */
private val Context.dataStore by preferencesDataStore(name = "select_car_prefs")

class SelectCarModuleImpl(private val appContext: Context) : SelectCarModule {

    /**
     * A [CoroutineDispatcher] designated for input/output operations.
     * In this implementation, [Dispatchers.IO] is used.
     */
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    /**
     * The [DataStore]<[Preferences]> instance used to store select car preferences.
     * This instance is lazily initialized using the extension property on [Context].
     */
    override val dataStore: DataStore<Preferences> by lazy {
        appContext.dataStore
    }

    /**
     * The [SelectCarRepository] implementation which uses the [dataStore] to retrieve or save car model
     */
    override val selectCarRepository: SelectCarRepository by lazy {
        SelectCarRepositoryImpl(dataStore)
    }

    /**
     * A container for the select car use cases.
     * This includes use cases for saving, retrieving, and validating the car model.
     */
    override val selectCarUseCases: SelectCarUseCases by lazy {
        SelectCarUseCases(
            saveCarModelUseCase = SaveCarModelUseCase(selectCarRepository),
            getCarModelUseCase = GetCarModelUseCase(selectCarRepository),
            validateCarModelUseCase = ValidateCarModelUseCase()
        )
    }

    /**
     * A [ViewModelProvider.Factory] used to obtain an instance of [SelectCarViewModel].
     * The factory is set up to provide the view model with the [ioDispatcher] and
     * [selectCarUseCases] dependencies.
     */
    override val selectCarViewModelFactory: ViewModelProvider.Factory by lazy {
        viewModelFactory { SelectCarViewModel(ioDispatcher, selectCarUseCases) }
    }
}

