package com.traffic.light.system.core.presentation.composables

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.traffic.light.system.R

/**
 * A simple circular progress indicator that is used to show the User ongoing operation
 *
 * @param modifier [Modifier] to apply to the indicator.
 * @param color The color of the progress indicator; defaults to the primary color.
 */
@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.colorPrimary),
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
    )
}