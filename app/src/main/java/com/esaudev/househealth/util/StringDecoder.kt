package com.esaudev.househealth.util

interface StringDecoder {
    fun decodeString(encodedString: String): String
    fun decodeNullableString(encodedString: String?): String?
}