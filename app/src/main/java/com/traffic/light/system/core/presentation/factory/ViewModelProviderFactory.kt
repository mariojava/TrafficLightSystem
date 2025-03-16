@file:Suppress("UNCHECKED_CAST")

package com.traffic.light.system.core.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Creates a [ViewModelProvider.Factory] that uses the given [initializer] to create ViewModel instances.
 *
 * @param initializer Lambda function to create a new instance of a [ViewModel].
 * @return A [ViewModelProvider.Factory] capable of instantiating the provided ViewModel.
 */
fun <VM : ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}