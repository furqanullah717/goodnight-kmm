package com.codewithfk.goodnight.sleep.presentation

import com.codewithfk.goodnight.sleep.domain.SleepModel

data class SleepState(val sleepTime: List<SleepModel>, val date: Long)