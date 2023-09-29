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

}