package com.example.androidintern.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidintern.data.Product

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
