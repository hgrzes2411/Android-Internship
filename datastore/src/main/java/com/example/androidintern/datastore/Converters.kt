package com.example.androidintern.datastore

import androidx.room.TypeConverter
import com.example.androidintern.datastore.database.CategoryEntity
import com.example.androidintern.datastore.model.Category

class Converters {

    @TypeConverter
    fun fromCategory(category: Category): String =
        category.name

    @TypeConverter
    fun toCategory(value: String): Category = Category.valueOf(value)

    @TypeConverter
    fun fromCategoryEntity(category: CategoryEntity): String = category.name

    @TypeConverter
    fun toCategoryEntity(value: String): CategoryEntity = CategoryEntity.valueOf(value)
}
