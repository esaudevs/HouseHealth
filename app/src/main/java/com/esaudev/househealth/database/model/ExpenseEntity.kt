package com.esaudev.househealth.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.domain.model.ServiceType
import java.time.LocalDateTime

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = HouseEntity::class,
            parentColumns = ["id"],
            childColumns = ["houseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpenseEntity(
    @PrimaryKey
    val id: String, val amount: Double,
    val houseId: String,
    val serviceType: ServiceType,
    val comments: String,
    val paidDate: LocalDateTime
)

fun ExpenseEntity.toExpense() = Expense(
    id = id,
    amount = amount,
    serviceType = serviceType,
    comments = comments,
    date = paidDate,
    houseId = houseId
)