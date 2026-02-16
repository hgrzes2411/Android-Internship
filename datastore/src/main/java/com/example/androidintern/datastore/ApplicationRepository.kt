package com.example.androidintern.datastore

import android.content.Context
import android.net.Uri
import com.example.androidintern.datastore.database.LocalProductDataSource
import com.example.androidintern.datastore.model.ApiProduct
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.model.fromDomainModel
import com.example.androidintern.datastore.model.toDomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

interface ProductsRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    fun getProductStream(id: Int): Flow<Product?>
    suspend fun insertProduct(product: Product, imageUri: Uri? = null)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun getProducts(page: Int, limit: Int): List<ApiProduct>
    suspend fun getProduct(id: Int): ApiProduct
}

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val localProductDataSource: LocalProductDataSource,
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
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

    override suspend fun getProducts(page: Int, limit: Int): List<ApiProduct> {
        val categories = listOf("CATEGORY1", "CATEGORY2", "CATEGORY3")
        val skip = (page - 1) * limit
        return apiService.getProducts(limit, skip).products.map { product ->
            product.copy(category = categories.random())
        }
    }

    override suspend fun getProduct(id: Int): ApiProduct {
        val categories = listOf("CATEGORY1", "CATEGORY2", "CATEGORY3")
        return apiService.getProduct(id).let {
            it.copy(category = categories.random())
        }
    }
}
