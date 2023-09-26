package com.codewithfk.goodnight.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class DateTimeKtx {

    fun getFormattedDate(
        iso8601Timestamp: Long,
    ): String {
        val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp)
        val date = localDateTime.date
        val day = date.dayOfMonth
        val month = date.monthNumber
        val year = date.year

        // This format should be generated based on an argument.
        // For now, we're hardcoding this to the 'dd.MM.yyyy' format.
        return "${day}/${month}/${year}"
    }

    fun getFormattedTime(
        iso8601Timestamp: Long,
    ): String {
        val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp)
        val date = localDateTime.time
        val hour = date.hour
        val minute = date.minute

        // This format should be generated based on an argument.
        // For now, we're hardcoding this to the 'dd.MM.yyyy' format.
        return "${hour}:${minute}"
    }

    private fun iso8601TimestampToLocalDateTime(timestamp: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }
}