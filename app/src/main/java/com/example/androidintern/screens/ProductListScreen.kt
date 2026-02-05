package com.example.androidintern.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidintern.di.AppContainer
import com.example.androidintern.ui.components.ClosetTopBar
import com.example.androidintern.ui.components.CircularIndicator
import com.example.androidintern.ui.components.ProductItem
import com.example.androidintern.viewmodels.ProductListViewModel
import com.example.androidintern.ui.R

@Composable
fun ProductListScreen(
    onAddClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onStoreClick: () -> Unit,
    productListViewModel: ProductListViewModel = viewModel(
        factory = ProductListViewModel.Factory(
            AppContainer(LocalContext.current).productsRepository
        )
    )
) {
    var isFabMenuOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = { ClosetTopBar() }) { padding ->
            val uiState by productListViewModel.uiState.collectAsState()

            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    CircularIndicator()
                } else if (uiState.products.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.no_items_in_closet),
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        userScrollEnabled = !isFabMenuOpen
                    ) {
                        items(uiState.products) { product ->
                            ProductItem(
                                product = product,
                                onProductClick = onProductClick
                            )
                        }
                    }
                }
            }
        }

        if (isFabMenuOpen) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { isFabMenuOpen = false }
                    ),
                color = Color.Black.copy(alpha = 0.6f)
            ) {}
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large),
                    top = dimensionResource(id = R.dimen.padding_large),
                    bottom = dimensionResource(id = R.dimen.fab_bottom_padding)
                ),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_height_medium))
            ) {
                if (isFabMenuOpen) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.store_title),
                            color = Color.White,
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_large))
                        )
                        Surface(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.fab_menu_item_size))
                                .clickable {
                                    isFabMenuOpen = false
                                    onStoreClick()
                                },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            content = {}
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.title_bar_text),
                            color = Color.White,
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_large))
                        )
                        Surface(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.fab_menu_item_size))
                                .clickable {
                                    isFabMenuOpen = false
                                    onAddClick()
                                },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            content = {}
                        )
                    }
                }
                FloatingActionButton(
                    onClick = { isFabMenuOpen = !isFabMenuOpen },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_new_item)
                    )
                }
            }
        }
    }
}
