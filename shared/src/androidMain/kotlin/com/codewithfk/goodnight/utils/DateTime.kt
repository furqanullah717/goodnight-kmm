package com.codewithfk.goodnight.utils

import android.annotation.SuppressLint
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

actual object DateTime {
    actual fun LocalDateTime.format(
        format: String
    ): String =
        SimpleDateFormat(format, Locale.getDefault()).format(
            toJavaLocalDateTime().toKotlinLocalDateTime().toInstant(
                TimeZone.currentSystemDefault()
            ).toEpochMilliseconds()
        )


    @SuppressLint("NewApi")
    actual fun getDateTime(string: String, format: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern(format)
        val zonedDateTime = ZonedDateTime.parse(string, formatter)
        return zonedDateTime.toLocalDateTime().toKotlinLocalDateTime()
    }
}