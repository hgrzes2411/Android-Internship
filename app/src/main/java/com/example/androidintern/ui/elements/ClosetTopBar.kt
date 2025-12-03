package com.example.androidintern.ui.elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.androidintern.R
import com.example.androidintern.ui.intermediate.CustomBanner

@Composable
fun ClosetTopBar() {
    CustomBanner(title = stringResource(id = R.string.closet_title))
}
