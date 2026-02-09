package com.example.androidintern.datastore.database

import com.example.androidintern.datastore.ProductDao
import com.example.androidintern.datastore.model.Product
import kotlinx.coroutines.flow.Flow

class LocalProductDataSource(private val productDao: ProductDao) {

    fun getAllProductsStream(): Flow<List<ProductEntity>> = productDao.getAllProducts()

    fun getProductStream(id: Int): Flow<ProductEntity?> = productDao.getProduct(id)

    suspend fun insertProduct(product: ProductEntity) = productDao.insert(product)

    suspend fun updateProduct(product: ProductEntity) = productDao.update(product)

    suspend fun deleteProduct(product: ProductEntity) = productDao.delete(product)
}