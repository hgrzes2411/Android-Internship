package com.example.androidintern.ui.intermediate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androidintern.ui.theme.TitleBarColor

@Composable
fun CustomBanner(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(TitleBarColor)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center
        )
    }
}
