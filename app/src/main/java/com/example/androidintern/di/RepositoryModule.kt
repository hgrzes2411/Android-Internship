package com.example.androidintern.di

import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.ProductsRepositoryImpl
import com.example.androidintern.datastore.UserRepository
import com.example.androidintern.datastore.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
