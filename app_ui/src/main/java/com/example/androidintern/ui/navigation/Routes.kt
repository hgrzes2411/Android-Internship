package com.example.androidintern.ui.navigation

object Routes {
    const val PRODUCT_LIST = "productList"
    const val ADD_PRODUCT = "addProduct"
    const val PRODUCT_DETAILS = "productDetails/{productId}?isRemote={isRemote}"
    const val STORE = "store"
    const val STORE_PRODUCT_DETAILS = "storeProductDetails/{productId}"
    const val PROFILE = "profile"

    fun productDetails(productId: String, isRemote: Boolean): String {
        return "productDetails/$productId?isRemote=$isRemote"
    }

    fun storeProductDetails(productId: String): String {
        return "storeProductDetails/$productId"
    }
}