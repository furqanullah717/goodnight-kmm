package com.codewithfk.goodnight.core.presentation


import androidx.compose.ui.window.ComposeUIViewController
import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.presentation.components.App
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme  = isDarkTheme,
        dynamicColor = false,
        appModule = AppModule(),
    )
}