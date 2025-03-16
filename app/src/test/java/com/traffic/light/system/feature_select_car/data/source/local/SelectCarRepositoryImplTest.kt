package com.traffic.light.system.feature_select_car.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.traffic.light.system.feature_select_car.data.model.PreferencesResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SelectCarRepositoryImplTest {
    private val testDataStore = mockk<DataStore<Preferences>>(relaxed = true)
    private val repository = SelectCarRepositoryImpl(testDataStore)

    @Test
    fun `saveCarModel returns Success on successful edit`() = runTest {
        val result = repository.saveCarModel("Mercedes")

        assertTrue(result is PreferencesResult.Success)
    }

    @Test
    fun `getCarModel returns corresponding value from preferences`() = runTest {
        val key = stringPreferencesKey(SelectCarRepositoryImpl.CAR_MODEL_PREFERENCE_KEY)

        val fakePreferences = mockk<Preferences>(relaxed = true) {
            coEvery { this@mockk[key] } returns "Opel"
        }

        coEvery { testDataStore.data } returns flowOf(fakePreferences)

        repository.getCarModel().collect { value ->
            assertTrue(value == "Opel")
        }
    }
}