package com.example.androidintern.datastore.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val image: String,
    val password: String? = null
)

data class UsersListResponse(
    val users: List<User>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String
)
