package com.codewithfk.goodnight.android

import android.os.Bundle

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.presentation.components.App
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : moe.tlaster.precompose.lifecycle.PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,

                appModule = AppModule(LocalContext.current.applicationContext),
            )
        }
    }
}