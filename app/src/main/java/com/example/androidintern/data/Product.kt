package com.example.androidintern.data

import androidx.annotation.DrawableRes

data class Product(
    @DrawableRes val imageRes: Int,
    val title: String,
    val description: String
)