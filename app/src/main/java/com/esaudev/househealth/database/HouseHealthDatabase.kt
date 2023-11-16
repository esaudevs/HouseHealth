package com.esaudev.househealth.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esaudev.househealth.database.converters.LocalDateTimeConverter
import com.esaudev.househealth.database.dao.ExpenseDao
import com.esaudev.househealth.database.dao.HouseDao
import com.esaudev.househealth.database.dao.HouseExpenseDao
import com.esaudev.househealth.database.dao.HouseExpenseTypeDao
import com.esaudev.househealth.database.model.ExpenseEntity
import com.esaudev.househealth.database.model.HouseEntity
import com.esaudev.househealth.database.model.HouseExpenseEntity
import com.esaudev.househealth.database.model.HouseExpenseTypeEntity

@TypeConverters(LocalDateTimeConverter::class)
@Database(
    entities = [
        HouseEntity::class,
        ExpenseEntity::class,
        HouseExpenseEntity::class,
        HouseExpenseTypeEntity::class
    ],
    version = 1
)
abstract class HouseHealthDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun houseExpenseDao(): HouseExpenseDao
    abstract fun houseExpenseTypeDao(): HouseExpenseTypeDao
}