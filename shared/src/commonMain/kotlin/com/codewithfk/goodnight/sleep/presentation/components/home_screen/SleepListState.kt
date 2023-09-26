package com.codewithfk.goodnight.sleep.presentation.components.home_screen

import com.codewithfk.goodnight.sleep.domain.SleepModel


data class SleepListState(
    val contacts: List<SleepModel> = emptyList(),
    val selectedContact: SleepModel? = null,
    val isAddSleepSheetOpen: Boolean = false,
    val isSelectedSleepSheetOpen: Boolean = false,
)