package com.codewithfk.goodnight.sleep.presentation.components.splash

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.codewithfk.goodnight.MR
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay
import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.lifecycle.LifecycleObserver
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    valid: Boolean?,
    onStart: () -> Unit,
    onSplashEndedValid: () -> Unit,
    onSplashEndedInvalid: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val color = remember { Animatable(Color.Black) }
    val currentValid = rememberUpdatedState(newValue = valid)

    LaunchedEffect(key1 = null) {
        delay(3000)
        if (currentValid.value == true) onSplashEndedValid()
        else onSplashEndedInvalid()
    }

    LaunchedEffect(key1 = valid) {
        valid?.let { valid ->
            val animateToColor = if (valid) Color.Green else Color.Red
            color.animateTo(animateToColor, animationSpec = tween(500))
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = null,
            painter = painterResource(MR.images.splash)
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = object : LifecycleObserver {
            override fun onStateChanged(state: Lifecycle.State) {
                if (state == Lifecycle.State.Initialized) {
                    onStart()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
