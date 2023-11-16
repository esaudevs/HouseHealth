package com.esaudev.househealth.domain.repository

import com.esaudev.househealth.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ExpensesRepository {

    suspend fun add(expense: Expense)

    fun observeById(expenseId: String): Flow<Expense>

    suspend fun deleteById(expenseId: String)

    fun observeAllByHouseMonth(
        houseId: String,
        date: LocalDateTime
    ): Flow<List<Expense>>

}