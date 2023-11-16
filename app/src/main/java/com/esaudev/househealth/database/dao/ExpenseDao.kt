package com.esaudev.househealth.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.househealth.database.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ExpenseDao {

    @Upsert
    suspend fun upsert(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    fun observeById(expenseId: String): Flow<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE houseId = :houseId AND strftime('%m', paidDate) = :monthValue")
    fun observeAllByHouseMonth(houseId: String, monthValue: String): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    fun deleteById(expenseId: String)

}