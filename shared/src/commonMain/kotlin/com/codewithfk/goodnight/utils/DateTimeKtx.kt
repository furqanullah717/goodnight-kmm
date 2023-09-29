package com.codewithfk.goodnight.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getLocalDateTimeFromLong(long: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(long).toLocalDateTime(TimeZone.currentSystemDefault())
}