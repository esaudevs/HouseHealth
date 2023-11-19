package com.esaudev.househealth.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.househealth.database.model.ExpenseEntity
import com.esaudev.househealth.domain.model.ServiceType
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Upsert
    suspend fun upsert(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    fun observeById(expenseId: String): Flow<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE houseId = :houseId AND strftime('%m', paidDate) = :monthValue AND serviceType IN (:serviceTypes)")
    fun observeAllByHouseMonth(
        houseId: String,
        monthValue: String,
        serviceTypes: List<ServiceType>
    ): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    fun deleteById(expenseId: String)
}