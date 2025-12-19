package com.example.androidintern.data

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    @DrawableRes val imageRes: Int
)
