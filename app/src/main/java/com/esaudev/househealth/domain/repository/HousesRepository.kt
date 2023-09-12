package com.esaudev.househealth.domain.repository

import com.esaudev.househealth.domain.model.House
import kotlinx.coroutines.flow.Flow

interface HousesRepository {

    suspend fun add(house: House)

    fun observeById(houseId: Int): Flow<House>

    suspend fun deleteById(houseId: Int)

    fun observeAll(): Flow<List<House>>
}