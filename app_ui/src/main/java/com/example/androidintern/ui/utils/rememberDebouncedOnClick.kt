package com.example.androidintern.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberDebouncedOnClick(onClick: () -> Unit): () -> Unit {
    val context = LocalContext.current
    return remember(context) {
        var lastClickTime = 0L
        val debounceInterval = 500 // 500ms
        {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceInterval) {
                lastClickTime = currentTime
                onClick()
            }
        }
    }
}