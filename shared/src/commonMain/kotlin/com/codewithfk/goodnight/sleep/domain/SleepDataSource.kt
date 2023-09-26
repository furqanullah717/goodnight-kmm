package com.codewithfk.goodnight.sleep.domain

import com.codewithfk.goodnight.sleep.domain.SleepModel
import kotlinx.coroutines.flow.Flow

interface SleepDataSource {


    fun getSleepData(): Flow<List<SleepModel>>
    fun getSleepDataByDate(date: Long): Flow<List<SleepModel>>
    suspend fun insertSleepData(sleepModel: SleepModel)
    suspend fun deleteSleepData(id: Long)

}