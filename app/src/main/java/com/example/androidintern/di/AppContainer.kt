package com.example.androidintern.di

import android.content.Context
import com.example.androidintern.datastore.ApiService
import com.example.androidintern.datastore.ApplicationRepository
import com.example.androidintern.datastore.InventoryDatabase
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.database.LocalProductDataSource
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
        ApplicationRepository(localProductDataSource, apiService, context)
    }
}
