package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time

import com.codewithfk.goodnight.sleep.domain.SleepModel


data class AddSleepTimeState(
    val selectedContact: SleepModel? = null,
    val isTimeError: Boolean = false,
    val isDateError: Boolean = false,
    val isTimerError: Boolean = false,
    val onAddedSuccess: Boolean = false
)