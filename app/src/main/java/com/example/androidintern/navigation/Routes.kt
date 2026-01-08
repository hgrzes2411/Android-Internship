package com.example.androidintern.navigation

object Routes {
    const val PRODUCT_LIST = "productList"
    const val ADD_PRODUCT = "addProduct"
    const val PRODUCT_DETAILS = "productDetails/{productId}"

    fun productDetails(productId: String): String {
        return "productDetails/$productId"
    }
}