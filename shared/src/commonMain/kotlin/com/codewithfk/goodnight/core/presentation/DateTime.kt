package com.codewithfk.goodnight.core.presentation

expect class DateTime() {
    fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String
}