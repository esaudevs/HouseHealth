package com.esaudev.househealth.domain.model

import com.esaudev.househealth.ext.getId
import java.time.LocalDateTime
import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().getId(),
    val amount: Double,
    val serviceType: ServiceType,
    val comments: String,
    val date: LocalDateTime
)