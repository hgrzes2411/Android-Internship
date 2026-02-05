package com.example.androidintern.datastore

import android.content.Context
import android.net.Uri
import com.example.androidintern.datastore.database.LocalProductDataSource
import com.example.androidintern.datastore.model.ApiProduct
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.model.fromDomainModel
import com.example.androidintern.datastore.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

interface ProductsRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    fun getProductStream(id: Int): Flow<Product?>
    suspend fun insertProduct(product: Product, imageUri: Uri? = null)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun getProducts(): List<ApiProduct>
    suspend fun getProduct(id: Int): ApiProduct
}

class ApplicationRepository(
    private val localProductDataSource: LocalProductDataSource,
    private val apiService: ApiService,
    private val context: Context
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

    override suspend fun insertProduct(product: Product, imageUri: Uri?) {
        val productToInsert = imageUri?.let {
            val imagePath = saveImage(it)
            product.copy(photoPath = imagePath)
        } ?: product
        localProductDataSource.insertProduct(productToInsert.fromDomainModel())
    }

    override suspend fun updateProduct(product: Product) = localProductDataSource.updateProduct(product.fromDomainModel())
    override suspend fun deleteProduct(product: Product) = localProductDataSource.deleteProduct(product.fromDomainModel())

    private suspend fun saveImage(imageUri: Uri): String {
        return withContext(Dispatchers.IO) {
            val a = UUID.randomUUID()
            val destinationFileName = "$a.jpg"
            val destinationFile = File(context.filesDir, destinationFileName)
            context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            destinationFile.absolutePath
        }
    }

    override suspend fun getProducts(): List<ApiProduct> {
        return apiService.getProducts()
    }

    override suspend fun getProduct(id: Int): ApiProduct {
        return apiService.getProduct(id)
    }
}
