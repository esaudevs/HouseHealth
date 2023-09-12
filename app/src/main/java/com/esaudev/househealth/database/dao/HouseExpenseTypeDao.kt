package com.esaudev.househealth.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.househealth.database.model.HouseExpenseTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseExpenseTypeDao {

    @Upsert
    suspend fun upsert(houseExpenseTypeEntity: HouseExpenseTypeEntity)

    @Query("SELECT * FROM house_expense_types WHERE id = :houseExpenseTypeId")
    fun observeById(houseExpenseTypeId: Int): Flow<HouseExpenseTypeEntity>

    @Query("SELECT * FROM house_expense_types")
    fun observeAll(): Flow<HouseExpenseTypeEntity>

    @Query("DELETE FROM house_expense_types WHERE id = :houseExpenseTypeId")
    fun deleteById(houseExpenseTypeId: Int)
}