package com.esaudev.househealth.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Singleton
    @Provides
    fun providesHouseDao(
        database: HouseHealthDatabase
    ) = database.houseDao()

    @Singleton
    @Provides
    fun providesExpenseDao(
        database: HouseHealthDatabase
    ) = database.expenseDao()

    @Singleton
    @Provides
    fun providesHouseExpenseDao(
        database: HouseHealthDatabase
    ) = database.houseExpenseDao()

    @Singleton
    @Provides
    fun providesHouseExpenseTypeDao(
        database: HouseHealthDatabase
    ) = database.houseExpenseTypeDao()
}