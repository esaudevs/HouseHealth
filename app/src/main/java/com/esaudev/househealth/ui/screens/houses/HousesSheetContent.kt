package com.esaudev.househealth.ui.screens.houses

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.House
import com.esaudev.househealth.ui.components.OutlinedTextFieldWithValidation
import com.esaudev.househealth.ui.components.ProgressButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HousesSheetContent(
    value: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    onAddHouseClick: (House) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Text(
        text = stringResource(id = R.string.add_house_title),
        style = MaterialTheme.typography.h4
    )
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextFieldWithValidation(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 8.dp
            ),
        valueProvider = { value },
        isErrorProvider = { hasError },
        errorMessageProvider = { stringResource(id = R.string.houses__error_empty_name) },
        onValueChange = onValueChange,
        label = stringResource(id = R.string.house_label),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    ProgressButton(
        isLoading = false,
        text = stringResource(id = R.string.add_house_button),
        onClick = {
            focusManager.clearFocus()
            keyboardController?.hide()
            onAddHouseClick(
                House(
                    name = value
                )
            )
        }
    )
}