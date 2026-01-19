package com.example.androidintern.data.model

import com.google.gson.annotations.SerializedName

data class ApiProduct(
    val id: Int,
    val title: String,
    @SerializedName("image")
    val image: String,
    val description: String,
    val category: String
)
