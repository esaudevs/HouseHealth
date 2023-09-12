package com.esaudev.househealth.ui.screens.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.getContent
import com.esaudev.househealth.ui.components.MonthSelector
import com.esaudev.househealth.ui.components.SelectAllServicesCard
import com.esaudev.househealth.ui.components.ServiceCard
import com.esaudev.househealth.ui.sheets.expenses.AddExpenseModalBottomSheet
import java.time.LocalDateTime

@Composable
fun ExpensesRoute(
    onExpenseClick: () -> Unit
) {
    ExpensesContent(onExpenseClick = onExpenseClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesContent(
    onExpenseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { isSheetOpen = true }) {
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
                        style = MaterialTheme.typography.titleMedium
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

        AddExpenseModalBottomSheet(
            sheetState = sheetState,
            isSheetOpen = isSheetOpen,
            onDismissRequest = {
                isSheetOpen = false
            },
            onSuccess = {
                isSheetOpen = false
            }
        )
    }
}