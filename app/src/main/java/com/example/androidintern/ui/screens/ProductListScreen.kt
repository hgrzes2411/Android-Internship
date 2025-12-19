package com.example.androidintern.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidintern.R
import com.example.androidintern.ui.elements.ClosetTopBar
import com.example.androidintern.ui.elements.IndeterminateCircularIndicator
import com.example.androidintern.ui.elements.ProductItem
import com.example.androidintern.ui.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(
    onAddClick: () -> Unit, onProductClick: (String) -> Unit, productListViewModel: ProductListViewModel = viewModel()
) {
    Scaffold(topBar = { ClosetTopBar() }, floatingActionButton = {
        FloatingActionButton(
            onClick = { productListViewModel.onAddClickDebounced(onAddClick) },
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_new_item)
            )
        }
    }) { padding ->
        val uiState by productListViewModel.uiState.collectAsState()

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                IndeterminateCircularIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(uiState.products) { product ->
                        ProductItem(product = product, modifier = Modifier.clickable { onProductClick(product.id) })
                    }
                }
            }
        }
    }
}
