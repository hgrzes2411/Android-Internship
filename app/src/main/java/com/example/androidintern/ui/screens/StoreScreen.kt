package com.example.androidintern.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.androidintern.data.model.Product
import com.example.androidintern.ui.elements.ProductItem
import com.example.androidintern.ui.intermediate.CustomBanner
import com.example.androidintern.ui.viewmodels.StoreViewModel

@Composable
fun StoreScreen(viewModel: StoreViewModel, onProductClick: (String) -> Unit) {
    val products by viewModel.products.collectAsState()

    Scaffold(topBar = { CustomBanner(title = "Store") }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(products) { product ->
                ProductItem(product = product, onProductClick = { onProductClick(product.id.toString()) })
            }
        }
    }
}
