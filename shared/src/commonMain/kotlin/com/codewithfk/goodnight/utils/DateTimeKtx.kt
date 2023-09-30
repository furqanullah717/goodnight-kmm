package com.codewithfk.goodnight.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


fun Int.zeroPrefixed(
    maxLength: Int,
): String {
    if (this < 0 || maxLength < 1) return ""

    val string = this.toString()
    val currentStringLength = string.length
    return if (maxLength <= currentStringLength) {
        string
    } else {
        val diff = maxLength - currentStringLength
        var prefixedZeros = ""
        repeat(diff) {
            prefixedZeros += "0"
        }
        "$prefixedZeros$string"
    }
}

fun getPairOfDateForRange(period: Int): Pair<Long, Long> {
    val nowTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val beforeTime =
        nowTime.toInstant(TimeZone.currentSystemDefault())
            .minus(DateTimePeriod(days = period), TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    val afterTime = nowTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    return Pair(beforeTime, afterTime)
}

fun getLocalDateTimeFromLong(long: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(long).toLocalDateTime(TimeZone.currentSystemDefault())
}