package com.codewithfk.goodnight.core.presentation

import com.codewithfk.goodnight.di.AppModule
import com.codewithfk.goodnight.sleep.presentation.components.App
import moe.tlaster.precompose.PreComposeApplication
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = PreComposeApplication() {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme  = isDarkTheme,
        dynamicColor = false,
        appModule = AppModule(),
    )
}