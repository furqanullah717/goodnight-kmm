package com.codewithfk.goodnight.di

import android.content.Context
import com.codewithfk.goodnight.core.data.DatabaseDriverFactory
import com.codewithfk.goodnight.shared.db.AppDatabase
import com.codewithfk.goodnight.sleep.data.SqlDelightSleepDataSource
import com.codewithfk.goodnight.sleep.domain.SleepDataSource

actual class AppModule(
    private val context: Context
) {
    actual val sleepDataSource: SleepDataSource by lazy {
        SqlDelightSleepDataSource(
            db = AppDatabase(
                driver = DatabaseDriverFactory(context).create(),
            ),
        )
    }

}