package com.example.androidintern.data.model

import com.example.androidintern.data.database.CategoryEntity
import com.example.androidintern.data.database.ProductEntity

fun ProductEntity.toDomainModel() = Product(
    id = id,
    title = title,
    category = category.toDomainModel(),
    description = description,
    photoPath = photoPath
)

fun Product.fromDomainModel() = ProductEntity(
    id = id,
    title = title,
    category = category.fromDomainModel(),
    description = description,
    photoPath = photoPath
)

fun CategoryEntity.toDomainModel(): Category {
    return enumValueOf(this.name)
}

fun Category.fromDomainModel(): CategoryEntity {
    return enumValueOf(this.name)
}