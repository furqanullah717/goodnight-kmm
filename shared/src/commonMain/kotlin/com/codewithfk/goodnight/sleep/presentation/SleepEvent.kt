package com.codewithfk.goodnight.sleep.presentation

sealed interface SleepEvent {
 object onAddSleepTime: SleepEvent
    object onGetSleepTime: SleepEvent
    object onNoDataEvent:SleepEvent

}