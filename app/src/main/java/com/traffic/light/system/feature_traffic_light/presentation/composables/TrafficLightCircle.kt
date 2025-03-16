package com.traffic.light.system.feature_traffic_light.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Draws a circle using a [Canvas] with the specified [color] and [size].
 *
 * @param color The color to fill the circle.
 * @param size The diameter of the circle.
 */
@Composable
fun TrafficLightCircle(color: Color, size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        drawCircle(color = color)
    }
}