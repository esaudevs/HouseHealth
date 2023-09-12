package com.esaudev.househealth.ui.sheets.houses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.House
import com.esaudev.househealth.ui.components.OutlinedTextFieldWithValidation
import com.esaudev.househealth.ui.components.ProgressButton
import com.esaudev.househealth.ui.components.SurfaceThemed
import com.esaudev.househealth.ui.util.UiTopLevelEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHouseModalBottomSheet(
    sheetState: SheetState,
    openSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: AddHouseModalViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiTopLevelEvent.collect { event ->
            when (event) {
                is UiTopLevelEvent.Success -> onSuccess()
                else -> Unit
            }
        }
    }

    if (openSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                viewModel.onEvent(AddHouseUiEvent.SheetDismissed)
                onDismissRequest()
            }
        ) {
            SheetContent(
                value = viewModel.uiState.name,
                hasError = viewModel.uiState.hasError,
                onValueChange = { houseName ->
                    viewModel.onEvent(AddHouseUiEvent.NameChanged(houseName))
                },
                onAddHouse = { house ->
                    viewModel.onEvent(AddHouseUiEvent.AddHouseClick(house))
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SheetContent(
    value: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    onAddHouse: (House) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_house_title),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextFieldWithValidation(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 8.dp
                ),
            value = value,
            isError = hasError,
            errorMessage = stringResource(id = R.string.houses__error_empty_name),
            onValueChange = onValueChange,
            label = stringResource(id = R.string.house_label),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProgressButton(
            isLoading = false,
            text = stringResource(id = R.string.add_house_button),
            onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onAddHouse(
                    House(
                        name = value
                    )
                )
            }
        )
    }
}

private class ErrorMessageProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(
            true,
            false
        )
}

@Preview
@Composable
private fun SheetContentPreview(
    @PreviewParameter(ErrorMessageProvider::class) hasErrorProvider: Boolean
) {
    SurfaceThemed {
        SheetContent(
            value = "Casa de Los Miras",
            hasError = hasErrorProvider,
            onValueChange = {},
            onAddHouse = {}
        )
    }
}