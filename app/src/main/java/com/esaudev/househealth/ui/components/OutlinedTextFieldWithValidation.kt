package com.esaudev.househealth.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldWithValidation(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    shape: Shape = RoundedCornerShape(50),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            shape = shape,
            textStyle = textStyle,
            singleLine = singleLine,
            isError = isError,
            supportingText = {
                AnimatedVisibility(visible = isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            },
            keyboardOptions = keyboardOptions
        )
    }
}

@Composable
@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun OutlinedTextFieldWithValidationPreview() {
    SurfaceThemed {
        Surface {
            OutlinedTextFieldWithValidation(
                value = "Value",
                isError = false,
                errorMessage = "Error message",
                onValueChange = {},
                label = "Label",
                keyboardActions = KeyboardActions { }
            )
        }
    }
}

@Composable
@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun OutlinedTextFieldWithValidationWithErrorPreview() {
    SurfaceThemed {
        Surface {
            OutlinedTextFieldWithValidation(
                value = "Value",
                isError = true,
                errorMessage = "Error message",
                onValueChange = {},
                label = "Label",
                keyboardActions = KeyboardActions { }
            )
        }
    }
}