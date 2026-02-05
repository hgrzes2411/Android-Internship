package com.example.androidintern.datastore.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val category: CategoryEntity = CategoryEntity.CATEGORY1,
    val description: String = "",
    val photoPath: String = ""
)
