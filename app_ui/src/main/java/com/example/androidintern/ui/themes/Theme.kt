package com.example.androidintern.ui.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.androidintern.ui.styles.LightColorScheme
import com.example.androidintern.ui.styles.typography

@Composable
fun AndroidInternTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography,
        content = content
    )
}
