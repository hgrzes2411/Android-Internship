package com.example.androidintern.datastore

import com.example.androidintern.datastore.model.ApiProduct
import com.example.androidintern.datastore.model.LoginResponse
import com.example.androidintern.datastore.model.ProductsListResponse
import com.example.androidintern.datastore.model.User
import com.example.androidintern.datastore.model.UsersListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int, @Query("skip") skip: Int): ProductsListResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ApiProduct

    @POST("auth/login")
    suspend fun login(@Body credentials: Map<String, String>): LoginResponse

    @GET("users")
    suspend fun getUsers(): UsersListResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User
}
