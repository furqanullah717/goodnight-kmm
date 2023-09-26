package com.codewithfk.goodnight.sleep.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codewithfk.goodnight.core.presentation.AppTheme
import com.codewithfk.goodnight.di.AppModule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {

    AppTheme(
        isSystemInDarkTheme()
    ) {
        Scaffold {
            Column(modifier = Modifier.fillMaxSize()) {
                for (i in 1..10) {
                    Text(text = "Hello World $i")
                }
            }
        }
    }


}