package com.codewithfk.goodnight.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter

actual object DateTime {
    actual fun LocalDateTime.format(format: String): String {
        val dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = format

        return dateFormatter.stringFromDate(this.toInstant(TimeZone.currentSystemDefault()).toNSDate())
    }

    actual fun getDateTime(string: String, format: String): LocalDateTime {
        val dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = format
        val date = dateFormatter.dateFromString(string)
            ?: throw IllegalStateException("Could not convert string to NSDate $string")
        return date.toKotlinInstant().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}