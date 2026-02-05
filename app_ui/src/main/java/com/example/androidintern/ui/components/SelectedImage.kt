package com.example.androidintern.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SelectedImage(modifier: Modifier = Modifier, selectedUri: Uri) {
    AsyncImage(
        model = selectedUri,
        contentDescription = null,
        modifier = modifier.size(180.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun SelectedImagePreview() {
    SelectedImage(selectedUri = Uri.EMPTY)
}