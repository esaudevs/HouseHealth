package com.esaudev.househealth.di

import com.esaudev.househealth.domain.repository.ExpensesRepository
import com.esaudev.househealth.domain.repository.ExpensesRepositoryImpl
import com.esaudev.househealth.domain.repository.HousesRepository
import com.esaudev.househealth.domain.repository.HousesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsHouseRepository(
        housesRepositoryImpl: HousesRepositoryImpl
    ): HousesRepository

    @Binds
    abstract fun bindsExpensesRepository(
        expensesRepositoryImpl: ExpensesRepositoryImpl
    ): ExpensesRepository
}