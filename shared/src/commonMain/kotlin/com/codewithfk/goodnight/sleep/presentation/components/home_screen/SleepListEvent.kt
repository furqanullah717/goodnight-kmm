package com.codewithfk.goodnight.sleep.presentation.components.home_screen

import com.codewithfk.goodnight.sleep.domain.SleepModel


sealed interface SleepListEvent {
    object OnAddNewSleepClick : SleepListEvent
    object DismissSleep : SleepListEvent
    data class OnFirstNameChanged(val value: String) : SleepListEvent
    data class OnLastNameChanged(val value: String) : SleepListEvent
    data class OnEmailChanged(val value: String) : SleepListEvent
    data class OnPhoneNumberChanged(val value: String) : SleepListEvent
    class OnPhotoPicked(val bytes: ByteArray) : SleepListEvent
    object OnAddPhotoClicked : SleepListEvent
    object SaveSleep : SleepListEvent
    data class SelectSleep(val sleepModel: SleepModel) : SleepListEvent
    data class EditSleep(val sleepModel: SleepModel) : SleepListEvent
    object DeleteSleep : SleepListEvent
}