package com.codewithfk.goodnight.sleep.presentation.components.stats

import com.codewithfk.goodnight.sleep.domain.HoursModel
import com.codewithfk.goodnight.sleep.domain.SleepDataSource
import com.codewithfk.goodnight.sleep.presentation.components.home_screen.SleepListState
import com.codewithfk.goodnight.utils.getPairOfDateForRange
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class StatsViewModel(private val dataSource: SleepDataSource) : ViewModel() {
    private val _state = getDate()
    val state: Flow<List<HoursModel>> = _state
    private fun getDate(): Flow<List<HoursModel>> {
        val pairTime = getPairOfDateForRange(7)
        return dataSource.getSleepDataByDateRange(pairTime.first, pairTime.second)
    }
}