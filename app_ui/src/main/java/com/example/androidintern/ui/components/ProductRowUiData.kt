package com.example.androidintern.ui.components

import androidx.compose.runtime.Immutable

@Immutable
data class ProductRowUiData(
    val title: String,
    val description: String,
    val category: String,
    val photoPath: String
)
