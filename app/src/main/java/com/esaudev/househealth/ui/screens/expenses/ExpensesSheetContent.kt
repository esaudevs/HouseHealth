package com.esaudev.househealth.ui.screens.expenses

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.getContent
import com.esaudev.househealth.ui.components.ExposedDropdownMenu
import com.esaudev.househealth.ui.components.OutlinedTextFieldWithValidation
import com.esaudev.househealth.ui.components.ProgressButton
import com.esaudev.househealth.ui.components.toExposedDropdownOption

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExpensesSheetContent(
    amount: String,
    comments: String,
    serviceType: ServiceType?,
    onAmountChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onServiceTypeChange: (ServiceType) -> Unit,
    onAddExpenseClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Text(
        text = stringResource(id = R.string.add_expense_title),
        style = MaterialTheme.typography.h4
    )
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextFieldWithValidation(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 8.dp
            ),
        value = amount,
        onValueChange = onAmountChange,
        label = stringResource(id = R.string.amount_label),
        isError = false,
        errorMessage = "",
        maxLines = 1,
        shape = RoundedCornerShape(50),
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    ExposedDropdownMenu(
        modifier = Modifier,
        options = ServiceType.values()
            .map {
                it.toExposedDropdownOption(labelToShow = it.getContent().name.asString())
            },
        selectedOption = serviceType.toExposedDropdownOption(
            labelToShow = serviceType?.getContent()?.name?.asString().orEmpty()
        ),
        onOptionSelected = { selection ->
            selection.optionObject?.let { onServiceTypeChange(it) }
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextFieldWithValidation(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 8.dp
            ),
        value = comments,
        onValueChange = onCommentChange,
        label = stringResource(id = R.string.comment_label),
        isError = false,
        errorMessage = "",
        maxLines = 1,
        shape = RoundedCornerShape(50),
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
    )
    Spacer(modifier = Modifier.height(8.dp))
    ProgressButton(
        isLoading = false,
        text = stringResource(id = R.string.add_expense_button),
        onClick = onAddExpenseClick
    )
}