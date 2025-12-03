package com.example.androidintern.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

private const val DEBOUNCE_INTERVAL_MS = 1000L

@Composable
fun rememberDebouncedOnClick(onClick: () -> Unit): () -> Unit {
    val debouncedState = remember {
        object {
            private var lastClickTime: Long = 0
            fun onDebouncedClick() {
                val now = System.currentTimeMillis()
                if (now - lastClickTime > DEBOUNCE_INTERVAL_MS) {
                    lastClickTime = now
                    onClick()
                }
            }
        }
    }
    return { debouncedState.onDebouncedClick() }
}
