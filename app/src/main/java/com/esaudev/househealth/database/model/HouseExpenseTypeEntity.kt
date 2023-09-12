package com.esaudev.househealth.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "house_expense_types")
data class HouseExpenseTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String
)