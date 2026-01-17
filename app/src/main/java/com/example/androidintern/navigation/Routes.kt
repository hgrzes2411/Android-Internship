package com.example.androidintern.navigation

object Routes {
    const val PRODUCT_LIST = "productList"
    const val ADD_PRODUCT = "addProduct"
    const val PRODUCT_DETAILS = "productDetails/{productId}"
    const val STORE = "store"
    const val STORE_PRODUCT_DETAILS = "storeProductDetails/{productId}"

    fun productDetails(productId: String): String {
        return "productDetails/$productId"
    }

    fun storeProductDetails(productId: String): String {
        return "storeProductDetails/$productId"
    }
}