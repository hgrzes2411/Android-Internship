package com.example.androidintern.ui.elements

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidintern.R
import com.example.androidintern.data.model.Product

@Composable
fun ProductImage(photoPath: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = if (photoPath.isNotBlank()) Uri.parse(photoPath) else R.drawable.placeholder,
        contentDescription = null,
        modifier = modifier
            .size(64.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProductRow(product: Product, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            ProductImage(photoPath = product.photoPath)
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = product.title, fontWeight = FontWeight.Bold)
                Text(text = product.category.name)
                Text(
                    text = product.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
