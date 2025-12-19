package com.example.androidintern.data

import com.example.androidintern.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceholderRepository {

    private val product = Product(
        id = "1",
        title = "Sample Product",
        description = "This is a sample product description.",
        category = "Sample Category",
        imageRes = R.drawable.ic_launcher_background
    )

    fun getData(): Flow<Product?> = flow {
        emit(null)
        // Delay data loading to simulate fetching the resource form external source.
        delay(3000)
        emit(product)
    }
}
