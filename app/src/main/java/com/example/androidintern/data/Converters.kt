package com.example.androidintern.data

import androidx.room.TypeConverter
import com.example.androidintern.data.database.CategoryEntity
import com.example.androidintern.data.model.Category

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
