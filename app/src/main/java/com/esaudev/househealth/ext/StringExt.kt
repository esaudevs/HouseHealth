package com.esaudev.househealth.ext

import java.util.Locale
import java.util.UUID

fun String.isNumeric(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.toSentence(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

fun UUID.getId(): String = UUID.randomUUID().toString()