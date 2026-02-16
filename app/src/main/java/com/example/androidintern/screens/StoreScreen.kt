package com.example.androidintern.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androidintern.ui.R
import com.example.androidintern.ui.components.Banner
import com.example.androidintern.ui.components.CircularIndicator
import com.example.androidintern.ui.components.ProductItem
import com.example.androidintern.ui.components.ProductItemUiData
import com.example.androidintern.viewmodels.StoreViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun StoreScreen(viewModel: StoreViewModel, onProductClick: (String) -> Unit) {
    val products by viewModel.products.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showRefreshPrompt by viewModel.showRefreshPrompt.collectAsState()
    val listState = rememberLazyListState()

    val pullRefreshState = rememberPullRefreshState(isLoading, { viewModel.onRefresh() })

    Scaffold(topBar = { Banner(title = stringResource(id = R.string.store_title)) }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .pullRefresh(pullRefreshState)
        ) {
            when {
                isLoading && products.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularIndicator()
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = error!!)
                    }
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
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
                        if (isLoading && products.isNotEmpty()) {
                            item {
                                Box(modifier = Modifier.fillParentMaxWidth(), contentAlignment = Alignment.Center) {
                                    CircularIndicator()
                                }
                            }
                        }
                    }

                    // Load more products when the user scrolls to the end of the list
                    val layoutInfo = listState.layoutInfo
                    val visibleItemsInfo = layoutInfo.visibleItemsInfo
                    LaunchedEffect(visibleItemsInfo) {
                        if (visibleItemsInfo.isNotEmpty() && visibleItemsInfo.last().index == products.size - 1) {
                            viewModel.loadMoreProducts()
                        }
                    }
                }
            }
        }

        if (showRefreshPrompt) {
            AlertDialog(
                onDismissRequest = { viewModel.onDismissRefreshPrompt() },
                title = { Text(text = stringResource(id = R.string.refresh_dialog_title)) },
                text = { Text(text = stringResource(id = R.string.refresh_prompt_text)) },
                confirmButton = {
                    Button(onClick = { viewModel.onRefresh() }) {
                        Text(text = stringResource(id = R.string.refresh_button_text))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.onDismissRefreshPrompt() }) {
                        Text(text = stringResource(id = R.string.refresh_dialog_dismiss_button))
                    }
                }
            )
        }
    }
}
