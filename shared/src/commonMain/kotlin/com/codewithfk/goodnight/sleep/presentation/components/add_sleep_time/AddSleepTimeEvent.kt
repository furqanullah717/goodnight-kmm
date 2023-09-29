package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time


sealed interface AddSleepTimeEvent {
    object OnSaveClicked : AddSleepTimeEvent
    object DismissSleep : AddSleepTimeEvent
}