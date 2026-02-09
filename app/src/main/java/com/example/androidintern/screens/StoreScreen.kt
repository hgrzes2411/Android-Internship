package com.example.androidintern.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androidintern.ui.R
import com.example.androidintern.ui.components.Banner
import com.example.androidintern.ui.components.ProductItem
import com.example.androidintern.ui.components.ProductItemUiData
import com.example.androidintern.viewmodels.StoreViewModel

@Composable
fun StoreScreen(viewModel: StoreViewModel, onProductClick: (String) -> Unit) {
    val products by viewModel.products.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(topBar = { Banner(title = stringResource(id = R.string.store_title)) }) { padding ->
        if (error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error!!)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(products) { product ->
                    ProductItem(
                        product = ProductItemUiData(
                            id = product.id.toString(),
                            title = product.title,
                            description = product.description,
                            photoPath = product.photoPath ?: ""
                        ),
                        onProductClick = onProductClick
                    )
                }
            }
        }
    }
}
