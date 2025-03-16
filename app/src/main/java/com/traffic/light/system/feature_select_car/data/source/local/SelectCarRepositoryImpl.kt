package com.traffic.light.system.feature_select_car.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import com.traffic.light.system.feature_select_car.data.source.local.SelectCarRepositoryImpl.Companion.CAR_MODEL_PREFERENCE_KEY
import com.traffic.light.system.feature_select_car.domain.repository.SelectCarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [SelectCarRepository] that handles saving and retrieving
 * the car model using Android's DataStore Preferences.
 *
 * This repository is responsible for persisting the car model value and providing
 * a way of obtaining changes related to it. It uses a key named [CAR_MODEL_PREFERENCE_KEY]
 * to store the car model as a string.
 *
 * @property dataStore The [DataStore] instance that provides the underlying storage mechanism.
 */
class SelectCarRepositoryImpl(private val dataStore: DataStore<Preferences>) : SelectCarRepository {

    companion object {
        // The key used to store and retrieve the car model from the preferences.
        const val CAR_MODEL_PREFERENCE_KEY = "carModel"
    }

    /**
     * Saves the provided car model into the DataStore.
     *
     * The function creates a preferences key from [CAR_MODEL_PREFERENCE_KEY] and attempts to
     * store the value `carModel`. It returns [PreferencesResult.Success] on successful saving.
     * If an exception occurs while editing the preferences, the exception is caught and a
     * [PreferencesResult.Error] is returned containing the thrown exception.
     *
     * @param carModel The car model to be saved.
     * @return [PreferencesResult.Success] if the operation completes successfully,
     *         otherwise [PreferencesResult.Error] containing the caught exception.
     */
    override suspend fun saveCarModel(carModel: String): PreferencesResult {
        val dataStoreKey = stringPreferencesKey(CAR_MODEL_PREFERENCE_KEY)
        return try {
            dataStore.edit { preferences ->
                preferences[dataStoreKey] = carModel
            }
            PreferencesResult.Success
        } catch (exception: Exception) {
            PreferencesResult.Error(exception)
        }
    }

    /**
     * Retrieves the saved car model from the DataStore.
     *
     * This method returns a [Flow] of nullable [String] values, where the car model value
     * is emitted each time the underlying preference changes. If the car model has not been saved
     * yet or is missing, the flow will emit `null`.
     *
     * @return A [Flow] emitting the car model as a [String?], or `null` if not available.
     */
    override suspend fun getCarModel(): Flow<String?> {
        val dataStoreKey = stringPreferencesKey(CAR_MODEL_PREFERENCE_KEY)
        return dataStore.data.map { preferences ->
            preferences[dataStoreKey]
        }
    }
}