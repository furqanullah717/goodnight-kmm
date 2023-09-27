package com.codewithfk.goodnight.utils

import kotlinx.datetime.LocalDateTime

expect object DateTime {
    fun LocalDateTime.format(format: String): String
    fun getDateTime(string: String, format: String): LocalDateTime
}