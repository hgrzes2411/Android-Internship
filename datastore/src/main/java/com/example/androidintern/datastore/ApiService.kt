package com.example.androidintern.datastore

import com.example.androidintern.datastore.model.ApiProduct
import com.example.androidintern.datastore.model.ProductsListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductsListResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ApiProduct
}