package com.codewithfk.goodnight.core.presentation

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource

expect class Strings {
    fun get(id: StringResource, args: List<Any>): String
}
@Composable
expect fun stringResource(id: StringResource,args: List<Any> =  emptyList()): String
