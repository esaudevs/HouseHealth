package com.esaudev.househealth.ext

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Double.getDecimalPart(): Double {
    return this % 1.0
}

fun Double.formatWithoutZeros(): String {
    val pattern = DecimalFormat("0.00")
    return pattern.format(this)
}

fun Double.formatToMoney(): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return currencyFormatter.format(this)
}