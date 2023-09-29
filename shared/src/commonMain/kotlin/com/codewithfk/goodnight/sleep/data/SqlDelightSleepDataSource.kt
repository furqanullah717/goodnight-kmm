package com.codewithfk.goodnight.sleep.data

import com.codewithfk.goodnight.shared.db.AppDatabase
import com.codewithfk.goodnight.sleep.domain.SleepDataSource
import com.codewithfk.goodnight.sleep.domain.SleepModel
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class SqlDelightSleepDataSource(db: AppDatabase) : SleepDataSource {
    private val queries = db.timeQueries
    override fun getSleepData(): Flow<List<SleepModel>> {
        return queries.getAllTime()
            .asFlow()
            .mapToList()
            .map { contactEntities ->
                supervisorScope {
                    contactEntities
                        .map {
                            async {
                                SleepModel(
                                    id = it.id.toInt(),
                                    startTime = it.startTime,
                                    endTime = it.endTime,
                                    date = it.date
                                )
                            }
                        }
                        .map { it.await() }
                }
            }
    }

    override fun getSleepDataByDate(date: Long): Flow<List<SleepModel>> {
        return queries.getSleepTimeByDate(date).asFlow()
            .mapToList()
            .map { contactEntities ->
                supervisorScope {
                    contactEntities
                        .map {
                            async {
                                SleepModel(
                                    id = it.id.toInt(),
                                    startTime = it.startTime,
                                    endTime = it.endTime,
                                    date = it.date
                                )
                            }
                        }
                        .map { it.await() }
                }
            }
    }

    override suspend fun insertSleepData(sleepModel: SleepModel) {
        queries.addSleepTime(
            id = sleepModel.id?.toLong(),
            date = sleepModel.date,
            startTime = sleepModel.startTime,
            endTime = sleepModel.endTime
        )
    }

    override suspend fun deleteSleepData(id: Long) {
        queries.deletSleepEntry(id)
    }
}