package com.esaudev.househealth.domain.model

import com.esaudev.househealth.database.model.ExpenseEntity
import com.esaudev.househealth.ext.getId
import java.time.LocalDateTime
import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().getId(),
    val amount: Double,
    val serviceType: ServiceType,
    val comments: String,
    val date: LocalDateTime,
    val houseId: String
)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    amount = amount,
    houseId = houseId,
    serviceType = serviceType,
    comments = comments,
    paidDate = date
)