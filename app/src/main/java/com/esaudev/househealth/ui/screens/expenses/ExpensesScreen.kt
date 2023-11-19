package com.esaudev.househealth.ui.screens.expenses

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.getContent
import com.esaudev.househealth.ui.components.EmptyPage
import com.esaudev.househealth.ui.components.ExpenseCard
import com.esaudev.househealth.ui.components.LoadingPage
import com.esaudev.househealth.ui.components.MonthSelector
import com.esaudev.househealth.ui.components.SelectAllServicesCard
import com.esaudev.househealth.ui.components.ServiceCard
import com.esaudev.househealth.ui.sheets.expenses.AddExpenseModalViewModel
import com.esaudev.househealth.ui.sheets.expenses.AddExpenseUiEvent
import com.esaudev.househealth.ui.theme.SolidWhite
import com.esaudev.househealth.ui.util.UiTopLevelEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.coroutines.launch

@Composable
fun ExpensesRoute(
    viewModel: ExpensesViewModel = hiltViewModel(),
    bottomViewModel: AddExpenseModalViewModel = hiltViewModel(),
    onExpenseClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val queryState by viewModel.queryState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getExpensesByQuery()
    }

    ExpensesScreen(
        uiState = uiState,
        queryState = queryState,
        bottomViewModel = bottomViewModel,
        onNextMonthClick = viewModel::onNextMonth,
        onPreviousMonthClick = viewModel::onPreviousMonth,
        onServiceClick = viewModel::onServiceClick,
        onAllServicesClick = viewModel::onAllServiceClick
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ExpensesScreen(
    uiState: ExpensesUiState,
    queryState: ExpensesQueryState,
    bottomViewModel: AddExpenseModalViewModel,
    onNextMonthClick: () -> Unit,
    onPreviousMonthClick: () -> Unit,
    onServiceClick: (ServiceType) -> Unit,
    onAllServicesClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    val dateDialogState = rememberMaterialDialogState()

    LaunchedEffect(key1 = true) {
        bottomViewModel.uiTopLevelEvent.collect { event ->
            when (event) {
                is UiTopLevelEvent.Success -> {
                    scope.launch {
                        modalSheetState.hide()
                    }
                }
                else -> Unit
            }
        }
    }

    if (modalSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                bottomViewModel.onEvent(AddExpenseUiEvent.SheetDismissed)
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            ExpensesSheetContent(
                amount = bottomViewModel.uiState.amount,
                comments = bottomViewModel.uiState.comments,
                serviceType = bottomViewModel.uiState.serviceType,
                date = bottomViewModel.uiState.date,
                onAmountChange = {
                    bottomViewModel.onEvent(AddExpenseUiEvent.AmountChanged(it))
                },
                onCommentChange = {
                    bottomViewModel.onEvent(AddExpenseUiEvent.CommentsChanged(it))
                },
                onServiceTypeChange = {
                    bottomViewModel.onEvent(AddExpenseUiEvent.ServiceTypeChanged(it))
                },
                onAddExpenseClick = {
                    bottomViewModel.onEvent(AddExpenseUiEvent.AddExpenseClick)
                },
                onMonthClick = {
                    dateDialogState.show()
                }
            )

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok", textStyle = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onBackground))
                    negativeButton(text = "Cancel", textStyle = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onBackground))
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                    onDateChange = {
                        bottomViewModel.onEvent(AddExpenseUiEvent.DateChanged(LocalDateTime.of(it, LocalTime.NOON)))
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp),
        sheetBackgroundColor = SolidWhite,
        sheetState = modalSheetState,
        content = {
            when (uiState) {
                is ExpensesUiState.HouseWithExpenses -> {
                    ExpensesWrapper(
                        uiState = uiState,
                        queryState = queryState,
                        onExpenseClick = {
                        },
                        onAddExpenseClick = {
                            bottomViewModel.initializeExpense()
                            scope.launch {
                                modalSheetState.show()
                            }
                        },
                        onNextMonthClick = onNextMonthClick,
                        onPreviousMonthClick = onPreviousMonthClick,
                        onServiceClick = onServiceClick,
                        onAllServicesClick = onAllServicesClick
                    )
                }

                is ExpensesUiState.Loading -> {
                    LoadingPage(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> Unit
            }
        }
    )
}

@Composable
fun ExpensesWrapper(
    modifier: Modifier = Modifier,
    uiState: ExpensesUiState.HouseWithExpenses,
    queryState: ExpensesQueryState,
    onNextMonthClick: () -> Unit,
    onPreviousMonthClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: () -> Unit,
    onServiceClick: (ServiceType) -> Unit,
    onAllServicesClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.open_add_expense_bottom_sheet_content_desc
                    )
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = 16.dp)
        ) {
            ExpensesHeader(
                queryState = queryState,
                onNextMonthClick = onNextMonthClick,
                onPreviousMonthClick = onPreviousMonthClick,
                onAllServicesClick = onAllServicesClick,
                onServiceClick = onServiceClick
            )

            AnimatedContent(targetState = uiState.expenses, label = "") { targetState ->
                if (targetState.isEmpty()) {
                    ExpensesEmptyContent(
                        paddingValues = paddingValues
                    )
                } else {
                    ExpensesWithContent(
                        uiState = uiState,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun ExpensesHeader(
    modifier: Modifier = Modifier,
    queryState: ExpensesQueryState,
    onNextMonthClick: () -> Unit,
    onPreviousMonthClick: () -> Unit,
    onAllServicesClick: () -> Unit,
    onServiceClick: (ServiceType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        MonthSelector(
            modifier = Modifier.fillMaxWidth(),
            date = queryState.date,
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.services_filter),
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ServiceType.values().onEach {
                ServiceCard(
                    serviceType = it,
                    onClick = onServiceClick,
                    isSelected = queryState.serviceTypes.contains(it),
                    enabled = !queryState.allServicesLocked
                )
            }
            SelectAllServicesCard(
                onClick = onAllServicesClick,
                isSelected = queryState.allServicesLocked
            )
        }
    }
}

@Composable
fun ExpensesEmptyContent(
    paddingValues: PaddingValues
) {
    EmptyPage(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        iconRes = R.drawable.ic_money_bill_solid,
        message = stringResource(id = R.string.houses__empty_expenses)
    )
}

@Composable
fun ExpensesWithContent(
    uiState: ExpensesUiState.HouseWithExpenses,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(uiState.expenses, key = { it.id }) {
            ExpenseCard(amount = it.amount, serviceType = it.serviceType)
        }

        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}