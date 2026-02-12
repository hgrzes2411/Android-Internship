package com.example.androidintern.datastore.model

import com.google.gson.annotations.SerializedName

data class ApiProduct(
    val id: Int,
    val title: String,
    @SerializedName("thumbnail")
    val image: String,
    val description: String,
    val category: String
)
