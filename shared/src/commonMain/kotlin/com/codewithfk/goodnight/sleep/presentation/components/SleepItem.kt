package com.codewithfk.goodnight.sleep.presentation.components


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.codewithfk.goodnight.core.presentation.AppTheme
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.presentation.components.home_screen.HomeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {

    AppTheme(
        darkTheme,
    ) {
        HomeScreen(appModule)
    }

}