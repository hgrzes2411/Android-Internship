package com.example.androidintern.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.androidintern.ui.R

@Composable
fun ClosetTopBar() {
    Banner(title = stringResource(id = R.string.closet_title))
}
