package com.esaudev.househealth.domain.repository

import com.esaudev.househealth.database.dao.HouseDao
import com.esaudev.househealth.database.model.toHouse
import com.esaudev.househealth.di.DefaultDispatcher
import com.esaudev.househealth.domain.model.House
import com.esaudev.househealth.domain.model.toEntity
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HousesRepositoryImpl @Inject constructor(
    private val houseDao: HouseDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : HousesRepository {
    override suspend fun add(house: House) {
        withContext(dispatcher) {
            houseDao.upsert(house.toEntity())
        }
    }

    override fun observeById(houseId: Int): Flow<House> {
        return houseDao.observeById(houseId = houseId).map {
            withContext(dispatcher) {
                it.toHouse()
            }
        }
    }

    override suspend fun deleteById(houseId: Int) {
        withContext(dispatcher) {
            houseDao.deleteById(houseId)
        }
    }

    override fun observeAll(): Flow<List<House>> {
        return houseDao.observeAll().map {
            withContext(dispatcher) {
                it.map { it.toHouse() }
            }
        }
    }
}