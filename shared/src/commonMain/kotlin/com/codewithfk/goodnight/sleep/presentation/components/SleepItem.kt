package com.codewithfk.goodnight.sleep.presentation.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.codewithfk.goodnight.MR
import com.codewithfk.goodnight.core.presentation.AppTheme
import com.codewithfk.goodnight.core.presentation.stringResource
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.presentation.components.add_sleep_time.AddSleepTimeScreen
import com.codewithfk.goodnight.sleep.presentation.components.home_screen.HomeScreen
import com.codewithfk.goodnight.sleep.presentation.components.splash.SplashScreen
import com.codewithfk.goodnight.sleep.presentation.components.stats.StatsScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {


    val navigator = rememberNavigator()
    AppTheme(
        darkTheme,
    ) {

        val home = stringResource(MR.strings.text_home)
        val stats = stringResource(MR.strings.text_stats)
        val profile = stringResource(MR.strings.text_profile)
        val showBottomBar = remember { mutableStateOf(false) }
        val selectedTab = remember { mutableStateOf(home) }
        Scaffold(floatingActionButton = {
            AnimatedVisibility(
                showBottomBar.value && selectedTab.value == home, enter = fadeIn(), exit = fadeOut()
            ) {

                Image(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                        navigator.navigate("/add")
                    }.background(MaterialTheme.colorScheme.tertiary).padding(16.dp),
                    imageVector = Icons.Default.Add,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary),
                    contentDescription = home
                )
            }
        }, bottomBar = {
            AnimatedVisibility(
                showBottomBar.value, enter = fadeIn(), exit = fadeOut()
            ) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BottomNavActionItem(
                            modifier = Modifier.weight(1f).fillMaxSize().clickable {
                                if (selectedTab.value != home) {
                                    navigator.navigate("/home")
                                    selectedTab.value = home
                                }
                            }, string = home, vector = Icons.Default.Home, selectedTab.value == home
                        )
                        BottomNavActionItem(
                            modifier = Modifier.weight(1f).fillMaxSize().clickable {
                                if (selectedTab.value != stats) {
                                    selectedTab.value = stats
                                    navigator.navigate("/stats")
                                }
                            },
                            string = stats,
                            vector = Icons.Default.BarChart,
                            b = selectedTab.value == stats
                        )
                        BottomNavActionItem(
                            modifier = Modifier.weight(1f).fillMaxSize().clickable {
                                if (selectedTab.value != profile) {
                                    selectedTab.value = profile
                                    navigator.navigate("/profile")
                                }
                            },
                            string = profile,
                            vector = Icons.Default.VerifiedUser,
                            b = selectedTab.value == profile
                        )
                    }
                }
            }
        }) {
            NavHost(
                modifier = Modifier.padding(it),
                // Assign the navigator to the NavHost
                navigator = navigator,
                // Navigation transition for the scenes in this NavHost, this is optional
                navTransition = NavTransition(
                    createTransition = slideInHorizontally(),
                    destroyTransition = slideOutHorizontally()
                ),
                // The start destination
                initialRoute = "/splash",
            ) {
                // Define a scene to the navigation graph
                scene(
                    // Scene's route path
                    route = "/splash",
                    // Navigation transition for this scene, this is optional
                ) {
                    showBottomBar.value = false
                    SplashScreen(
                        modifier = Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary),
                        onSplashEndedInvalid = {},
                        onSplashEndedValid = {
                            navigator.navigate(
                                "/home", NavOptions(popUpTo = PopUpTo("/splash", true))
                            )
                        },
                        onStart = {},
                        valid = true
                    )
                }
                scene(
                    // Scene's route path
                    route = "/home",
                    // Navigation transition for this scene, this is optional
                    navTransition = NavTransition(
                        createTransition = fadeIn(),
                        destroyTransition = fadeOut()
                    )
                ) {
                    showBottomBar.value = true
                    HomeScreen(appModule, navigator)
                }
                scene(
                    // Scene's route path
                    route = "/add",
                    // Navigation transition for this scene, this is optional
                ) {
                    showBottomBar.value = false
                    AddSleepTimeScreen(appModule, navigator)
                }
                scene(
                    // Scene's route path
                    route = "/stats",
                    // Navigation transition for this scene, this is optional
                    navTransition =
                    NavTransition(
                        createTransition = fadeIn(),
                        destroyTransition = fadeOut()
                    )
                ) {
                    showBottomBar.value = true
                    StatsScreen(appModule, navigator)
                }
            }
        }
    }
}

@Composable
fun BottomNavActionItem(modifier: Modifier, string: String, vector: ImageVector, b: Boolean) {
    val color = if (b) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = vector,
            contentDescription = string,
            modifier = Modifier,
            colorFilter = ColorFilter.tint(color)
        )
        Text(string, color = color)
    }

}