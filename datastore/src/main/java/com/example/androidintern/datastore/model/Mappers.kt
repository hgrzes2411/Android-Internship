package com.example.androidintern.datastore.model

import com.example.androidintern.datastore.database.CategoryEntity
import com.example.androidintern.datastore.database.ProductEntity

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
    photoPath = photoPath ?: ""
)

fun CategoryEntity.toDomainModel(): Category {
    return Category.valueOf(this.name)
}

fun Category.fromDomainModel(): CategoryEntity {
    return CategoryEntity.valueOf(this.name)
}

fun ApiProduct.toDomainModel() = Product(
    id = id,
    title = title,
    photoPath = image,
    description = description,
    category = category.toCategory()
)

private fun String.toCategory(): Category {
    return when (this) {
        "CATEGORY1" -> Category.CATEGORY1
        "CATEGORY2" -> Category.CATEGORY2
        "CATEGORY3" -> Category.CATEGORY3
        else -> Category.CATEGORY1
    }
}
