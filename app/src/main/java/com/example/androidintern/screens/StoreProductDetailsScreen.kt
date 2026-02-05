package com.example.androidintern.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.androidintern.di.AppContainer
import com.example.androidintern.ui.R
import com.example.androidintern.ui.components.CircularIndicator
import com.example.androidintern.viewmodels.ProductDetailsViewModel

@Composable
fun StoreProductDetailsScreen(productId: String) {
    val appContainer = AppContainer(LocalContext.current)
    val viewModel: ProductDetailsViewModel = viewModel(
        factory = ProductDetailsViewModel.provideFactory(
            productsRepository = appContainer.productsRepository,
            productId = productId.toInt(),
            isRemote = true
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                CircularIndicator()
            }
        } else if (uiState.product == null) {
            Text(
                text = stringResource(id = R.string.item_not_found),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            uiState.product?.let { product ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    AsyncImage(
                        model = product.photoPath,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(id = R.dimen.product_details_image_height))
                            .clip(MaterialTheme.shapes.large),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product.title,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_small)))
                                Text(
                                    text = product.category.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Button(onClick = { viewModel.saveProduct() }) {
                                Text(text = stringResource(id = R.string.add_button_text))
                            }
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_medium)))
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
