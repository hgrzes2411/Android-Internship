package com.example.androidintern.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.androidintern.R
import com.example.androidintern.di.AppContainer
import com.example.androidintern.ui.elements.IndeterminateCircularIndicator
import com.example.androidintern.ui.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(productId: String) {
    val appContainer = AppContainer(LocalContext.current)
    val viewModel: ProductDetailsViewModel = viewModel(
        factory = ProductDetailsViewModel.Factory(
            appContainer.productsRepository,
            productId.toInt()
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                IndeterminateCircularIndicator()
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
                        model = product.photoPath.toUri(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp) // Set a fixed height for the image
                            .clip(MaterialTheme.shapes.large),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = product.category.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
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
