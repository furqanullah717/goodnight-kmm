package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time

import com.codewithfk.goodnight.sleep.domain.SleepDataSource
import com.codewithfk.goodnight.sleep.domain.SleepModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddSleepTimeViewModel(private val contactDataSource: SleepDataSource) : ViewModel() {

    private val _state = MutableStateFlow(AddSleepTimeState())
    val state =
        _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AddSleepTimeState())

    fun addItem(newItem: SleepModel) {
        viewModelScope.launch {
            newItem.let {
                contactDataSource.insertSleepData(it)
                _state.update { it.copy(onAddedSuccess = true) }
            }
        }
    }
}