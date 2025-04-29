package com.invictastudios.moviesapp.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvents( // Observes a [Flow] of one-time events and handles them
    events: Flow<T>, // used for UI event handling like showing toasts, where the event should only be handled once and only while the UI is active.
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { // [events] are only collected when the [Lifecycle] is in the [Lifecycle.State.STARTED] state, avoiding memory leaks and unnecessary work when the UI is not visible.
            withContext(Dispatchers.Main.immediate) {// Because StateFlow sometimes skips collecting data, this is used as a workaround to make sure data is not lost
                events.collect(onEvent)
            }
        }
    }
}
