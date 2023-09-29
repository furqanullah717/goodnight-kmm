package com.codewithfk.goodnight.core.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class Strings(val context: Context) {
    actual fun get(
        id: StringResource,
        args: List<Any>
    ): String {
        return if (args.isEmpty()) {
            StringDesc.Resource(id).toString(context = context)
        } else {
            id.format(*args.toTypedArray()).toString(context)
        }
    }
}

@Composable
actual fun stringResource(
    id: StringResource,
    args: List<Any>
): String {

    return Strings(LocalContext.current).get(id,args)
}