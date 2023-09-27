package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.domain.SleepModel
import com.codewithfk.goodnight.utils.DateTime.format
import com.codewithfk.goodnight.utils.getLocalDateTimeFromLong
import com.mohamedrejeb.calf.ui.datepicker.AdaptiveDatePicker
import com.mohamedrejeb.calf.ui.datepicker.rememberAdaptiveDatePickerState
import com.mohamedrejeb.calf.ui.sheet.AdaptiveBottomSheet
import com.mohamedrejeb.calf.ui.sheet.rememberAdaptiveSheetState
import com.mohamedrejeb.calf.ui.timepicker.AdaptiveTimePicker
import com.mohamedrejeb.calf.ui.timepicker.rememberAdaptiveTimePickerState
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSleepTimeScreen(appModule: AppModule, navigator: Navigator) {
    val viewModel = getViewModel(key = "add_time_screen", factory = viewModelFactory {
        AddSleepTimeViewModel(appModule.sleepDataSource)
    })

    var enableButton by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberAdaptiveSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by remember { mutableStateOf(false) }

    val startTimeSheet = rememberAdaptiveSheetState(skipPartiallyExpanded = true)
    var openStartTimeSheet by remember { mutableStateOf(false) }
    val startTimePickerState = rememberAdaptiveTimePickerState()

    val endTimeSheetState = rememberAdaptiveSheetState(skipPartiallyExpanded = true)
    var openEndTimeSheet by remember { mutableStateOf(false) }
    val endTimePicker = rememberAdaptiveTimePickerState()


    val datePickerState = rememberAdaptiveDatePickerState()
    var data by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            data = getLocalDateTimeFromLong(it).format("dd/MMM/yyyy")
        }
    }

    val time = getTimeProgress(
        datePickerState.selectedDateMillis,
        startTimePickerState.hour,
        startTimePickerState.minute
    )


    val endT = getTimeProgress(
        datePickerState.selectedDateMillis,
        endTimePicker.hour,
        endTimePicker.minute
    )


    LaunchedEffect(
        time
    ) {
        startTime = time
    }


    LaunchedEffect(
        endT
    ) {
        endTime = endT
    }


    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary),
        ) {
            Image(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.padding(16.dp).align(
                    Alignment.CenterStart
                ).clickable { navigator.goBack() },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
            Text(
                "Add Sleep Time",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }

        enableButton = data.isNotEmpty() && isFirstTimeLesser(
            startTimePickerState.hour,
            startTimePickerState.minute,
            endTimePicker.hour,
            endTimePicker.minute
        )


    }) {
        Surface(modifier = Modifier.padding(it)) {
            val state by viewModel.state.collectAsState()

            LaunchedEffect(state) {
                if (state.onAddedSuccess) {
                    navigator.goBack()
                }
            }
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = data,
                    modifier = Modifier.fillMaxWidth().focusable(false).clickable {
                        scope.launch {
                            openBottomSheet = true
                            sheetState.show()

                        }
                    },
                    onValueChange = { data = it },
                    placeholder = { Text(text = "Enter Date") },
                    label = { Text(text = "Enter Date") },
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = MaterialTheme.colorScheme.primary,
                        disabledLabelColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = startTime,
                    modifier = Modifier.fillMaxWidth().focusable(false).clickable {
                        scope.launch {
                            openStartTimeSheet = true
                            startTimeSheet.show()
                        }
                    },
                    onValueChange = { startTime = it },
                    placeholder = { Text(text = "Enter Start Time") },
                    label = { Text(text = "Enter Start Time") },
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = MaterialTheme.colorScheme.primary,
                        disabledLabelColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = endTime,
                    modifier = Modifier.fillMaxWidth().focusable(false).clickable {
                        scope.launch {
                            openEndTimeSheet = true
                            endTimeSheetState.show()

                        }
                    },
                    onValueChange = { endTime = it },
                    placeholder = { Text(text = "Enter End Time") },
                    label = { Text(text = "Enter End Time") },
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = MaterialTheme.colorScheme.primary,
                        disabledLabelColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
            if (openBottomSheet) {
                AdaptiveBottomSheet(
                    onDismissRequest = { openBottomSheet = false },
                    adaptiveSheetState = sheetState,

                    ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Select Date",
                        )
                        AdaptiveDatePicker(
                            state = datePickerState,
                        )
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    openBottomSheet = false
                                }
                            }
                        }) {
                            Text("Save")

                        }
                        Spacer(Modifier.size(16.dp))
                    }
                }
            }

            if (openStartTimeSheet) {
                AdaptiveBottomSheet(
                    onDismissRequest = { openStartTimeSheet = false },
                    adaptiveSheetState = startTimeSheet,

                    ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Select Date",
                        )
                        AdaptiveTimePicker(
                            state = startTimePickerState
                        )
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            scope.launch { startTimeSheet.hide() }.invokeOnCompletion {
                                if (!startTimeSheet.isVisible) {
                                    openStartTimeSheet = false
                                }
                            }
                        }) {
                            Text("Save")

                        }
                        Spacer(Modifier.size(16.dp))
                    }
                }
            }

            if (openEndTimeSheet) {
                AdaptiveBottomSheet(
                    onDismissRequest = { openEndTimeSheet = false },
                    adaptiveSheetState = endTimeSheetState,

                    ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Select Date",
                        )
                        AdaptiveTimePicker(
                            state = endTimePicker
                        )
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            scope.launch { endTimeSheetState.hide() }.invokeOnCompletion {
                                if (!startTimeSheet.isVisible) {
                                    openEndTimeSheet = false
                                }
                            }
                        }) {
                            Text("Save")

                        }
                        Spacer(Modifier.size(16.dp))
                    }
                }
            }


            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Button(
                    onClick = {
                        viewModel.addItem(
                            SleepModel(
                                null,
                                Clock.System.now().toEpochMilliseconds(),
                                Clock.System.now().toEpochMilliseconds(),
                                Clock.System.now().toEpochMilliseconds()
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    enabled = enableButton
                ) {
                    Text(
                        text = "Add Sleep",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

fun isFirstTimeLesser(startHour: Int, startMin: Int, endHour: Int, endMin: Int): Boolean {
    val startTime = LocalTime(startHour, startMin)
    val endTime = LocalTime(endHour, endMin)

    return startTime < endTime
}

fun getTimeProgress(date: Long?, hour: Int, minute: Int): String {
    val localDate = getLocalDateTimeFromLong(date ?: Clock.System.now().toEpochMilliseconds())
    val localTime = LocalTime(hour, minute)
    val localObj = LocalDateTime(localDate.date, localTime)
    return localObj.format("hh:mm a")

}