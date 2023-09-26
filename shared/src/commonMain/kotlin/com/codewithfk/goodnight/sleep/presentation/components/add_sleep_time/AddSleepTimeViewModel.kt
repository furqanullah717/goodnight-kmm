package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time

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

class AddSleepTimeViewModel(private val contactDataSource: SleepDataSource) : ViewModel() {

    private val _state = MutableStateFlow(AddSleepTimeState())
    val state =
        _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AddSleepTimeState())

    fun addItem(newItem: SleepModel) {
        viewModelScope.launch {
            newItem?.let {
                contactDataSource.insertSleepData(it)
                _state.update { it.copy(onAddedSuccess = true) }
            }
        }
    }
}