package com.codewithfk.goodnight.sleep.presentation.components.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.codewithfk.goodnight.sleep.domain.SleepDataSource
import com.codewithfk.goodnight.sleep.domain.SleepModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(private val contactDataSource: SleepDataSource) : ViewModel() {

    private val _state = MutableStateFlow(SleepListState())
    val state = combine(
        _state,
        contactDataSource.getSleepData(),
    ) { state, contacts ->
        state.copy(
            contacts = contacts,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SleepListState())


    init {
        viewModelScope.launch {
            for (i in 1..10) {
                contactDataSource.insertSleepData(
                    SleepModel(
                        id = null,
                        date = Clock.System.now().toEpochMilliseconds(),
                        startTime = Clock.System.now().toEpochMilliseconds() * i,
                        endTime = Clock.System.now().toEpochMilliseconds(),
                    )
                )
            }
        }
    }

    var newItem: SleepModel? by mutableStateOf(null)
        private set

    fun onEvent(event: SleepListEvent) {
        when (event) {
            SleepListEvent.DeleteSleep -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let { id ->
                        _state.update {
                            it.copy(
                                isSelectedSleepSheetOpen = false
                            )
                        }
                        contactDataSource.deleteSleepData(id.toLong())
                        delay(300L) // Animation delay
                        _state.update {
                            it.copy(
                                selectedContact = null
                            )
                        }
                    }
                }
            }

            SleepListEvent.DismissSleep -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isSelectedSleepSheetOpen = false,
                            isAddSleepSheetOpen = false,
                        )
                    }
                    delay(300L) // Animation delay
                    newItem = null
                    _state.update {
                        it.copy(
                            selectedContact = null
                        )
                    }
                }
            }

            is SleepListEvent.EditSleep -> {
                _state.update {
                    it.copy(
                        selectedContact = null,
                        isAddSleepSheetOpen = true,
                        isSelectedSleepSheetOpen = false
                    )
                }
                newItem = event.sleepModel
            }

            SleepListEvent.OnAddNewSleepClick -> {
                _state.update {
                    it.copy(
                        isAddSleepSheetOpen = true
                    )
                }
                newItem = SleepModel(
                    id = null,
                    startTime = 0,
                    endTime = 0,
                    date = 0
                )
            }

            SleepListEvent.SaveSleep -> {
                newItem?.let { contact ->

                    _state.update {
                        it.copy(
                            isAddSleepSheetOpen = false,
                        )
                    }
                    viewModelScope.launch {
                        contactDataSource.insertSleepData(contact)
                        delay(300L) // Animation delay
                        newItem = null
                    }

                }
            }

            is SleepListEvent.SelectSleep -> {
                _state.update {
                    it.copy(
                        selectedContact = event.sleepModel,
                        isSelectedSleepSheetOpen = true
                    )
                }
            }

            else -> Unit
        }
    }
}