package com.codewithfk.goodnight.sleep.presentation.components.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.utils.DateTime
import com.codewithfk.goodnight.utils.DateTime.format
import com.codewithfk.goodnight.utils.getLocalDateTimeFromLong
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.navigation.Navigator
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(appModule: AppModule, navigator: Navigator) {
    val viewModel = getViewModel(key = "home_screen", factory = viewModelFactory {
        HomeViewModel(appModule.sleepDataSource)
    })
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) { Text("Home", color = MaterialTheme.colorScheme.onPrimary) }
        }
    ) {
        val state = viewModel.state.collectAsState()

        LazyColumn(
            modifier = Modifier.padding(it).padding(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {

            items(state.value.contacts.size) { index ->

                val color = remember(state.value.contacts[index].id) {
                    Color(
                        Random.nextInt(0, 255),
                        Random.nextInt(0, 255),
                        Random.nextInt(0, 255),
                        100
                    ).value
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = Color(color)
                        ).padding(16.dp)

                ) {
                    Text(
                        text = getLocalDateTimeFromLong(state.value.contacts[index].date).format("dd/MMM/yyyy")
                    )


                    Text(
                        text = "${
                            getLocalDateTimeFromLong(state.value.contacts[index].startTime).format("hh:mm a")
                        } - ${
                            getLocalDateTimeFromLong(state.value.contacts[index].endTime).format("hh:mm a")
                        }"
                    )

                }

            }
        }
    }
}