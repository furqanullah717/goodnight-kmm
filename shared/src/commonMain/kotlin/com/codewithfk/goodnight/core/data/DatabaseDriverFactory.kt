package com.codewithfk.goodnight.core.data

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
     fun create(): SqlDriver
}