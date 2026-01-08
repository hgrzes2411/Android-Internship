package com.example.androidintern.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidintern.data.database.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: Int): Flow<ProductEntity?>

    @Query("SELECT * FROM products ORDER BY title ASC")
    fun getAllProducts(): Flow<List<ProductEntity>>
}
