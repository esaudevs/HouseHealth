package com.esaudev.househealth.domain.repository

import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.ui.screens.expenses.ExpensesQueryState
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    suspend fun add(expense: Expense)

    fun observeById(expenseId: String): Flow<Expense>

    suspend fun deleteById(expenseId: String)

    fun observeAllByQuery(
        houseId: String,
        query: ExpensesQueryState
    ): Flow<List<Expense>>
}