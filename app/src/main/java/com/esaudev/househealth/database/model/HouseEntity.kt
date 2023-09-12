package com.esaudev.househealth.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esaudev.househealth.domain.model.House

@Entity(tableName = "houses")
data class HouseEntity(
    @PrimaryKey()
    val id: String,
    val name: String
)

fun HouseEntity.toHouse() = House(
    id = id,
    name = name
)