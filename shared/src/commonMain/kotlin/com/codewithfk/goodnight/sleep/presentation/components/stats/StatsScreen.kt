package com.codewithfk.goodnight.sleep.presentation.components.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.codewithfk.goodnight.MR
import com.codewithfk.goodnight.core.presentation.stringResource
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.utils.DateTime
import com.codewithfk.goodnight.utils.DateTime.format
import com.codewithfk.goodnight.utils.getLocalDateTimeFromLong
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.navigation.Navigator


@Composable
fun StatsScreen(appModule: AppModule, navigator: Navigator) {
    val viewModel = getViewModel(key = "stats_screen", factory = viewModelFactory {
        StatsViewModel(appModule.sleepDataSource)
    })
    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
        ) {
            Text(
                stringResource(MR.strings.text_home),
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

    }) {
        Surface(modifier = Modifier.padding(it)) {
            val state = viewModel.state.collectAsState(emptyList())
            state.value.let { date ->
                val listOfData = mutableListOf<Double>()
                val dates = mutableListOf<String>()
                date.forEach {
                    listOfData.add(it.hours.toDouble() ?: 0.0)
                    dates.add(getLocalDateTimeFromLong(it.date!!).format("dd/MM"))
                }

                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    if (listOfData.isNotEmpty()) {
                        val testLineParameters: List<LineParameters> = listOf(
                            LineParameters(
                                label = "Last 7 Days Sleep",
                                data = listOfData,
                                lineColor = MaterialTheme.colorScheme.primary,
                                lineType = LineType.DEFAULT_LINE,
                                lineShadow = true,
                            ),
                        )
                        LineChart(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            linesParameters = testLineParameters,
                            isGrid = true,
                            gridColor = Color.Blue,
                            xAxisData = dates,
                            animateChart = true,
                            showGridWithSpacer = true,
                            yAxisStyle = TextStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                            xAxisStyle = TextStyle(
                                fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.W400
                            ),
                            yAxisRange = 14,
                            oneLineChart = false,
                            gridOrientation = GridOrientation.VERTICAL
                        )
                        val testChartParameters: List<BarParameters> = listOf(
                            BarParameters(
                                dataName = "Last 7 Days Sleep",
                                data = listOfData,
                                barColor = MaterialTheme.colorScheme.primary,
                            ),
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                            BarChart(
                                chartParameters = testChartParameters,
                                gridColor = Color.Blue,
                                xAxisData = dates,
                                animateChart = true,
                                showGridWithSpacer = true,
                                yAxisStyle = TextStyle(
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                ),
                                xAxisStyle = TextStyle(
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W400
                                ),
                                yAxisRange = 14,
                            )
                        }

                    }
                }


            }
        }
    }
}