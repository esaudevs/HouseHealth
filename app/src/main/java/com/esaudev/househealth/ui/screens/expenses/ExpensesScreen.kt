package com.esaudev.househealth.ui.screens.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.getContent
import com.esaudev.househealth.ui.components.MonthSelector
import com.esaudev.househealth.ui.components.SelectAllServicesCard
import com.esaudev.househealth.ui.components.ServiceCard
import com.esaudev.househealth.ui.sheets.expenses.AddExpenseModalViewModel
import com.esaudev.househealth.ui.sheets.expenses.AddExpenseUiEvent
import com.esaudev.househealth.ui.theme.SolidWhite
import com.esaudev.househealth.ui.util.UiTopLevelEvent
import java.time.LocalDateTime
import kotlinx.coroutines.launch

@Composable
fun ExpensesRoute(
    onExpenseClick: () -> Unit,
    bottomViewModel: AddExpenseModalViewModel = hiltViewModel()
) {
    ExpensesScreen(
        bottomViewModel = bottomViewModel
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ExpensesScreen(
    bottomViewModel: AddExpenseModalViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

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
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                ExpensesSheetContent(
                    amount = bottomViewModel.uiState.amount,
                    comments = bottomViewModel.uiState.comments,
                    serviceType = bottomViewModel.uiState.serviceType,
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
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp),
        sheetBackgroundColor = SolidWhite,
        sheetState = modalSheetState,
        content = {
            ExpensesContent(
                onExpenseClick = {
                },
                onAddExpenseClick = {
                    scope.launch {
                        modalSheetState.show()
                    }
                }
            )
        }
    )
}

@Composable
fun ExpensesContent(
    onExpenseClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAddExpenseClick: () -> Unit
) {
    Scaffold(
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    MonthSelector(
                        modifier = Modifier.fillMaxWidth(),
                        date = LocalDateTime.now(),
                        onPreviousMonthClick = {},
                        onNextMonthClick = {}
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
                            ServiceCard(serviceTypeContent = it.getContent(), onClick = {})
                        }
                        SelectAllServicesCard(
                            onClick = { },
                            isSelected = false
                        )
                    }
                }
            }
        }
    }
}