package com.esaudev.househealth.domain.repository

import com.esaudev.househealth.database.dao.ExpenseDao
import com.esaudev.househealth.database.model.toExpense
import com.esaudev.househealth.di.DefaultDispatcher
import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.domain.model.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
): ExpensesRepository {

    override suspend fun add(expense: Expense) {
        withContext(dispatcher) {
            expenseDao.upsert(expense.toEntity())
        }
    }

    override fun observeById(expenseId: String): Flow<Expense> {
        return expenseDao.observeById(expenseId = expenseId).map {
            withContext(dispatcher) {
                it.toExpense()
            }
        }
    }

    override suspend fun deleteById(expenseId: String) {
        withContext(dispatcher) {
            expenseDao.deleteById(expenseId = expenseId)
        }
    }

    override fun observeAllByHouseMonth(
        houseId: String,
        date: LocalDateTime
    ): Flow<List<Expense>> {
        return expenseDao.observeAllByHouseMonth(houseId = houseId, date.monthValue.toString()).map {
            withContext(dispatcher) {
                it.map { it.toExpense() }
            }
        }
    }
}