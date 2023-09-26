package com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.domain.SleepModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.datetime.Clock
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSleepTimeScreen(appModule: AppModule, navigator: Navigator) {
    val viewModel = getViewModel(key = "add_time_screen", factory = viewModelFactory {
        AddSleepTimeViewModel(appModule.sleepDataSource)
    })

    Scaffold(
        topBar = {
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
                    "Home", color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    ) {
        var data by remember { mutableStateOf("") }
        var startTime by remember { mutableStateOf("") }
        var endTime by remember { mutableStateOf("") }
        val state by viewModel.state.collectAsState()

        LaunchedEffect(state) {
            if (state.onAddedSuccess) {
                navigator.goBack()
            }
        }
        Column(modifier = Modifier.fillMaxWidth().padding(it).padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                value = data,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { data = it },
                placeholder = { Text(text = "Enter Date") },
                label = { Text(text = "Enter Date") }
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(value = startTime,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { startTime = it },
                placeholder = { Text(text = "Enter Start Time") },
                label = { Text(text = "Enter Start Time") }
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(value = endTime,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { endTime = it },
                placeholder = { Text(text = "Enter End Time") },
                label = { Text(text = "Enter End Time") }
            )
            Spacer(modifier = Modifier.size(10.dp))
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
                modifier = Modifier.fillMaxWidth(),
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