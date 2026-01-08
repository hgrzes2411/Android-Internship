package com.example.androidintern.di

import android.content.Context
import com.example.androidintern.data.DefaultProductsRepository
import com.example.androidintern.data.InventoryDatabase
import com.example.androidintern.data.ProductsRepository
import com.example.androidintern.data.database.LocalProductDataSource

class AppContainer(context: Context) {

    private val localProductDataSource: LocalProductDataSource by lazy {
        LocalProductDataSource(InventoryDatabase.getDatabase(context).productDao())
    }

    val productsRepository: ProductsRepository by lazy {
        DefaultProductsRepository(localProductDataSource)
    }
}
