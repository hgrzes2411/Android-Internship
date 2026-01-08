package com.example.androidintern.data

import com.example.androidintern.data.database.LocalProductDataSource
import com.example.androidintern.data.database.ProductEntity
import com.example.androidintern.data.model.Product
import com.example.androidintern.data.model.fromDomainModel
import com.example.androidintern.data.model.toDomainModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface ProductsRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    fun getProductStream(id: Int): Flow<Product?>
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
}

class DefaultProductsRepository(
    private val localProductDataSource: LocalProductDataSource
) : ProductsRepository {
    override fun getAllProductsStream(): Flow<List<Product>> = localProductDataSource.getAllProductsStream()
        .map { list -> list.map { it.toDomainModel() } }
        .onStart {
            delay(3000)  // Pretending to load data
        }

    override fun getProductStream(id: Int): Flow<Product?> =
        localProductDataSource.getProductStream(id).map { it?.toDomainModel() }
            .onStart {
                delay(3000) // Pretending to load data
            }

    override suspend fun insertProduct(product: Product) = localProductDataSource.insertProduct(product.fromDomainModel())
    override suspend fun updateProduct(product: Product) = localProductDataSource.updateProduct(product.fromDomainModel())
    override suspend fun deleteProduct(product: Product) = localProductDataSource.deleteProduct(product.fromDomainModel())
}
