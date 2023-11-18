package com.esaudev.househealth.domain.usecase

import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.ui.sheets.expenses.AddExpenseUiState
import java.time.LocalDateTime
import javax.inject.Inject

class ValidateExpenseStateUseCase @Inject constructor() {
    suspend operator fun invoke(uiState: AddExpenseUiState): Result<Expense> {
        if (uiState.serviceType == null) {
            return Result.failure(Exception())
        }

        if (uiState.houseId == null) {
            return Result.failure(Exception())
        }

        return Result.success(
            Expense(
                amount = uiState.amount.toDouble(),
                serviceType = uiState.serviceType,
                comments = uiState.comments,
                date = uiState.date,
                houseId = uiState.houseId
            )
        )
    }
}