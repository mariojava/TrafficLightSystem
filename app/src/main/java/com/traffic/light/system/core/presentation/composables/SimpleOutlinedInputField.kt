package com.traffic.light.system.core.presentation.composables

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.traffic.light.system.R

/**
 * A configurable material outlined text field with a label, placeholder, and error visualization support.
 *
 * @param modifier custom Modifier to style or position the text field differently.
 * @param label The label displayed above the field.
 * @param value The current text value; if empty, [initialValue] is used.
 * @param initialValue The initial text value when [value] is empty.
 * @param placeholder Text shown when the field is empty.
 * @param errorResource Resource ID for the error message; if greater than 0, an error is displayed.
 * @param isSingleLine Whether the text field is limited to a single line.
 * @param isEnabled Controls whether input is accepted.
 * @param mainColor Primary color used for focused states and error indicators.
 * @param onChange Callback invoked when the text value changes.
 */
@Composable
fun SimpleOutlinedInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    initialValue: String = "",
    placeholder: String = "",
    errorResource: Int = 0,
    isSingleLine: Boolean = true,
    isEnabled: Boolean = true,
    mainColor: Color = colorResource(R.color.colorPrimary),
    onChange: (String) -> Unit = {}
) {
    val input = initialValue.ifEmpty {
        value
    }

    val blackColor = colorResource(R.color.black)
    val grayColor = colorResource(R.color.gray)
    val whiteColor = colorResource(R.color.white)
    val redColor = colorResource(R.color.red)

    OutlinedTextField(
        value = input,
        label = { Text(label) },
        modifier = modifier,
        singleLine = isSingleLine,
        enabled = isEnabled,
        placeholder = { Text(placeholder) },
        isError = errorResource > 0,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = mainColor,
            unfocusedLabelColor = blackColor,
            focusedTextColor = blackColor,
            unfocusedTextColor = grayColor,
            unfocusedContainerColor = whiteColor,
            focusedContainerColor = whiteColor,
            focusedIndicatorColor = mainColor,
            unfocusedIndicatorColor = mainColor,
            disabledContainerColor = whiteColor,
            disabledLabelColor = grayColor,
            errorSupportingTextColor = redColor,
            errorIndicatorColor = redColor,
            errorContainerColor = whiteColor
        ),
        supportingText = @Composable {
            if (errorResource > 0) {
                Text(
                    text = stringResource(errorResource)
                )
            }
        },
        onValueChange = {
            onChange(it)
        }
    )
}