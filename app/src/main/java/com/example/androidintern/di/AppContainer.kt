package com.example.androidintern.di

import android.content.Context
import com.example.androidintern.data.ApiService
import com.example.androidintern.data.DefaultProductsRepository
import com.example.androidintern.data.InventoryDatabase
import com.example.androidintern.data.ProductsRepository
import com.example.androidintern.data.RemoteProductsRepository
import com.example.androidintern.data.database.LocalProductDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {

    private val localProductDataSource: LocalProductDataSource by lazy {
        LocalProductDataSource(InventoryDatabase.getDatabase(context).productDao())
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val productsRepository: ProductsRepository by lazy {
        DefaultProductsRepository(localProductDataSource, context)
    }

    val remoteProductsRepository: RemoteProductsRepository by lazy {
        RemoteProductsRepository(apiService)
    }
}
