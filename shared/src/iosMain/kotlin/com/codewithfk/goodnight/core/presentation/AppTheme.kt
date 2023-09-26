package com.codewithfk.goodnight.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.codewithfk.goodnight.ui.theme.DarkColors
import com.codewithfk.goodnight.ui.theme.LightColors
import com.codewithfk.goodnight.ui.theme.Typography

@Composable
actual fun AppTheme(isDarkMode: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isDarkMode) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}