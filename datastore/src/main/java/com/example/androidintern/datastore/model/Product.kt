package com.example.androidintern.datastore.model

data class Product(
    val id: Int = 0,
    val title: String = "",
    val category: Category = Category.CATEGORY1,
    val description: String = "",
    val photoPath: String? = null
)
