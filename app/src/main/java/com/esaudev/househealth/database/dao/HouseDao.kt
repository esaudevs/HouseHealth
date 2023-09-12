package com.esaudev.househealth.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.househealth.database.model.HouseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseDao {

    @Upsert
    suspend fun upsert(houseEntity: HouseEntity)

    @Query("SELECT * FROM houses WHERE id = :houseId")
    fun observeById(houseId: Int): Flow<HouseEntity>

    @Query("SELECT * FROM houses")
    fun observeAll(): Flow<List<HouseEntity>>

    @Query("DELETE FROM houses WHERE id = :houseId")
    fun deleteById(houseId: Int)
}