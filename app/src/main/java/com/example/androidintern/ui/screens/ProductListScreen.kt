package com.example.androidintern.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidintern.R
import com.example.androidintern.di.AppContainer
import com.example.androidintern.ui.elements.ClosetTopBar
import com.example.androidintern.ui.elements.IndeterminateCircularIndicator
import com.example.androidintern.ui.elements.ProductRow
import com.example.androidintern.ui.viewmodels.ProductListViewModel

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
                    IndeterminateCircularIndicator()
                } else if (uiState.products.isEmpty()) {
                    Text(
                        text = "No items in the closet",
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
                            ProductRow(
                                product = product,
                                modifier = Modifier.clickable(enabled = !isFabMenuOpen) { onProductClick(product.id.toString()) })
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
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isFabMenuOpen) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Store",
                            color = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Surface(
                            modifier = Modifier
                                .size(15.dp)
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
                            text = "Custom",
                            color = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Surface(
                            modifier = Modifier
                                .size(15.dp)
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
