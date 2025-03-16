package com.traffic.light.system.core.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.traffic.light.system.R

/**
 * Displays an material outlined button with customizable text, colors, and click event handler.
 *
 * @param modifier potential custom Modifier to be applied to this button.
 * @param buttonText The text displayed on the button.
 * @param mainColor The color for the button content when enabled.
 * @param borderColor The border color when enabled.
 * @param isEnabled Controls whether the button is enabled or not.
 * @param onClick Callback invoked when the button is clicked.
 */
@Composable
fun SimpleOutlinedButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    mainColor: Color = colorResource(R.color.colorPrimary),
    borderColor: Color = colorResource(R.color.colorPrimary),
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    val whiteColor = colorResource(R.color.white)
    val grayColor = colorResource(R.color.gray)

    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(
            dimensionResource(R.dimen.border_size),
            if (isEnabled) borderColor else grayColor
        ),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = whiteColor,
            contentColor = whiteColor,
            disabledContentColor = whiteColor,
            disabledContainerColor = whiteColor
        ),
        onClick = { onClick() }) {
        Text(buttonText, color = if (isEnabled) mainColor else grayColor)
    }
}