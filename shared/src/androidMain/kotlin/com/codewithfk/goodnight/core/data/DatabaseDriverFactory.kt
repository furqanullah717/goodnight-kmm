package com.codewithfk.goodnight.core.data


import android.content.Context
import com.codewithfk.goodnight.shared.db.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            context,
            "sleep.db"
        )
    }
}