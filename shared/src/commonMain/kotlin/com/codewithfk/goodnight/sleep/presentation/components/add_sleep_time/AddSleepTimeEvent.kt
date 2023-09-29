package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time

import com.codewithfk.goodnight.sleep.domain.SleepModel


sealed interface AddSleepTimeEvent {
    object OnSaveClicked : AddSleepTimeEvent
    object DismissSleep : AddSleepTimeEvent
}