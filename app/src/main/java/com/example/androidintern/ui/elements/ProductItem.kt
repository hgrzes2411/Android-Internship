package com.example.androidintern.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidintern.data.model.Product

@Composable
fun ProductItem(product: Product, onProductClick: (Product) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable { onProductClick(product) }) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.photoPath,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = product.title, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.description, style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
