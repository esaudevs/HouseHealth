package com.esaudev.househealth.ui.sheets.expenses

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseModalBottomSheet(
    sheetState: SheetState,
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: AddExpenseModalViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiTopLevelEvent.collect { event ->
            when (event) {
                is UiTopLevelEvent.Success -> onSuccess()
                else -> Unit
            }
        }
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                viewModel.onEvent(AddExpenseUiEvent.SheetDismissed)
                onDismissRequest()
            }
        ) {
            SheetContent(
                amountValue = viewModel.uiState.amount,
                commentValue = viewModel.uiState.comments,
                serviceTypeValue = viewModel.uiState.serviceType,
                onCommentValueChange = { comments ->
                    viewModel.onEvent(AddExpenseUiEvent.CommentsChanged(comments))
                },
                onAmountValueChange = { amount ->
                    viewModel.onEvent(AddExpenseUiEvent.AmountChanged(amount))
                },
                onServiceTypeChange = { serviceType ->
                    viewModel.onEvent(AddExpenseUiEvent.ServiceTypeChanged(serviceType))
                },
                onAddExpense = { expense ->
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SheetContent(
    amountValue: String,
    commentValue: String,
    serviceTypeValue: ServiceType?,
    onAmountValueChange: (String) -> Unit,
    onCommentValueChange: (String) -> Unit,
    onServiceTypeChange: (ServiceType) -> Unit,
    onAddExpense: (Expense) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_expense_title),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 8.dp
                ),
            value = amountValue,
            onValueChange = onAmountValueChange,
            label = {
                Text(
                    text = stringResource(id = R.string.amount_label),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            maxLines = 1,
            shape = RoundedCornerShape(50),
            textStyle = MaterialTheme.typography.bodyMedium,
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
            selectedOption = serviceTypeValue.toExposedDropdownOption(
                labelToShow = serviceTypeValue?.getContent()?.name?.asString().orEmpty()
            ),
            onOptionSelected = { selection ->
                selection.optionObject?.let { onServiceTypeChange(it) }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 8.dp
                ),
            value = commentValue,
            onValueChange = onCommentValueChange,
            label = {
                Text(
                    text = stringResource(id = R.string.comment_label),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            maxLines = 1,
            shape = RoundedCornerShape(50),
            textStyle = MaterialTheme.typography.bodyMedium,
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
            onClick = {
            }
        )
    }
}

@Preview
@Composable
private fun SheetContentPreview() {
    SurfaceThemed {
        SheetContent(
            amountValue = "50",
            commentValue = "Pagado con tarjeta",
            serviceTypeValue = null,
            onAmountValueChange = {},
            onCommentValueChange = {},
            onServiceTypeChange = {},
            onAddExpense = {}
        )
    }
}*/