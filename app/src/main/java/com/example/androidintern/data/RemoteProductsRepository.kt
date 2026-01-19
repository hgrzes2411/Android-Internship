package com.example.androidintern.data

import com.example.androidintern.data.model.ApiProduct

class RemoteProductsRepository(private val apiService: ApiService) {

    suspend fun getProducts(): List<ApiProduct> {
        return apiService.getProducts()
    }

    suspend fun getProduct(id: Int): ApiProduct {
        return apiService.getProduct(id)
    }
}
