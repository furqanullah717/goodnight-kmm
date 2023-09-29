package com.codewithfk.goodnight.di

import com.codewithfk.goodnight.core.data.DatabaseDriverFactory
import com.codewithfk.goodnight.shared.db.AppDatabase
import com.codewithfk.goodnight.sleep.data.SqlDelightSleepDataSource
import com.codewithfk.goodnight.sleep.domain.SleepDataSource

actual class AppModule {
    actual val sleepDataSource: SleepDataSource by lazy {
        SqlDelightSleepDataSource(
            db = AppDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }
}