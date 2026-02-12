package com.example.androidintern.di

import android.content.Context
import com.example.androidintern.datastore.ApiService
import com.example.androidintern.datastore.InventoryDatabase
import com.example.androidintern.datastore.ProductDao
import com.example.androidintern.datastore.database.LocalProductDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInventoryDatabase(@ApplicationContext context: Context): InventoryDatabase {
        return InventoryDatabase.getDatabase(context)
    }

    @Provides
    fun provideProductDao(database: InventoryDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideLocalProductDataSource(productDao: ProductDao): LocalProductDataSource {
        return LocalProductDataSource(productDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
