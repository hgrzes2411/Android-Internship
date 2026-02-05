package com.example.androidintern.ui.components

import androidx.compose.runtime.Immutable

@Immutable
data class ProductItemUiData(
    val id: String,
    val title: String,
    val description: String,
    val photoPath: String
)
