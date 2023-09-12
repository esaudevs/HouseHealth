package com.esaudev.househealth.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "house_expenses",
    foreignKeys = [
        ForeignKey(
            entity = HouseEntity::class,
            parentColumns = ["id"],
            childColumns = ["houseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HouseExpenseTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["houseExpenseTypeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HouseExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val houseId: Int,
    val houseExpenseTypeId: Int,
    val concept: String,
    val amount: Double,
    val paymentDate: LocalDateTime
)