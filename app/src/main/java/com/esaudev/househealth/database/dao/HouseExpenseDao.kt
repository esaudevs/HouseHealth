package com.esaudev.househealth.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.househealth.database.model.HouseExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseExpenseDao {

    @Upsert
    suspend fun upsert(houseExpenseEntity: HouseExpenseEntity)

    @Query("SELECT * FROM house_expenses WHERE id = :houseExpenseId")
    fun observeById(houseExpenseId: Int): Flow<HouseExpenseEntity>

    @Query("SELECT * FROM house_expenses")
    fun observeAll(): Flow<HouseExpenseEntity>

    @Query("DELETE FROM house_expenses WHERE id = :houseExpenseId")
    fun deleteById(houseExpenseId: Int)
}