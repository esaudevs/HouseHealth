package com.esaudev.househealth.ext

import java.util.UUID

fun String.isNumeric(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun UUID.getId(): String = UUID.randomUUID().toString()