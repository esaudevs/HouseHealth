package com.esaudev.househealth.domain.model

import com.esaudev.househealth.database.model.HouseEntity
import com.esaudev.househealth.ext.getId
import java.util.UUID

data class House(
    val id: String = UUID.randomUUID().getId(),
    val name: String
)

fun House.toEntity() = HouseEntity(
    id = id,
    name = name
)