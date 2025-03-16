package com.traffic.light.system.feature_traffic_light.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.traffic.light.system.R
import com.traffic.light.system.feature_traffic_light.presentation.model.TrafficLightState

@Composable
fun TrafficLightForm(carModel: String, trafficLightState: TrafficLightState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = carModel,
            fontSize = dimensionResource(R.dimen.text_size_large).value.sp
        )

        val circleSize = dimensionResource(R.dimen.traffic_light_circle_size)
        val commonSpacing = dimensionResource(R.dimen.padding_large)
        val inactiveTrafficLightColor = colorResource(R.color.traffic_light_inactive)

        Spacer(modifier = Modifier.height(commonSpacing))

        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.black), shape = RoundedCornerShape(
                        dimensionResource(R.dimen.traffic_light_corner_size)
                    )
                )
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(commonSpacing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TrafficLightCircle(
                    color = if (trafficLightState is TrafficLightState.Red) colorResource(R.color.traffic_light_red) else inactiveTrafficLightColor,
                    size = circleSize
                )
                TrafficLightCircle(
                    color = if (trafficLightState is TrafficLightState.Orange) colorResource(R.color.traffic_light_orange) else inactiveTrafficLightColor,
                    size = circleSize
                )
                TrafficLightCircle(
                    color = if (trafficLightState is TrafficLightState.Green) colorResource(R.color.traffic_light_green) else inactiveTrafficLightColor,
                    size = circleSize
                )
            }
        }
    }
}