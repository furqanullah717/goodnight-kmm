package com.codewithfk.goodnight.di

import com.codewithfk.goodnight.sleep.domain.SleepDataSource

expect class AppModule {
    val sleepDataSource: SleepDataSource
}