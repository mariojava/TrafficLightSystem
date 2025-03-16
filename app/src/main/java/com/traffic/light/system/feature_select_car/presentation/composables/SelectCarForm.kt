package com.traffic.light.system.feature_select_car.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.traffic.light.system.R
import com.traffic.light.system.core.presentation.composables.CircularProgress
import com.traffic.light.system.core.presentation.composables.SimpleOutlinedButton
import com.traffic.light.system.core.presentation.composables.SimpleOutlinedInputField

@Composable
fun SelectCarForm(
    carModelState: String,
    isLoadingInitialCarModel: Boolean,
    isProcessingSelectedCarInfo: Boolean,
    errorResource: Int,
    saveCarModelError: String,
    onChangeCarModel: (String) -> Unit,
    onSelectCarModel: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val context = LocalContext.current
        val isFormLoading = isLoadingInitialCarModel || isProcessingSelectedCarInfo

        SimpleOutlinedInputField(
            initialValue = carModelState,
            label = stringResource(R.string.car_model_label),
            placeholder = stringResource(
                R.string.car_model_placeholder
            ),
            errorResource = errorResource,
            isEnabled = !isFormLoading
        ) {
            onChangeCarModel(it)
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
        SimpleOutlinedButton(
            buttonText = stringResource(R.string.start_driving_button_text),
            isEnabled = !isFormLoading
        ) {
            // TODO: validate car model first and if has error - add supporting text to the input field, change color of indicator
            keyboardController?.hide() // hide keyboard on potential click just for better UX
            onSelectCarModel(carModelState)
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Box(
            modifier = Modifier
                .height(dimensionResource(R.dimen.progress_size))
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            // Show visual indicator that the last entered car model is currently loading
            if (isFormLoading) {
                CircularProgress()
            }
        }

        if (saveCarModelError.isNotEmpty()) {
            LaunchedEffect(true) {
                Toast.makeText(context, saveCarModelError, Toast.LENGTH_SHORT).show()
            }
        }
    }
}