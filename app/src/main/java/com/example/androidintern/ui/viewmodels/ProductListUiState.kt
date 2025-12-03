package com.example.androidintern.ui.viewmodels

import com.example.androidintern.data.Product

data class ProductListUiState(
    val products: List<Product> = emptyList()
)
