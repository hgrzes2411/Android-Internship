package com.example.androidintern.ui.intermediate

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidintern.R

@Composable
fun SelectedImage(modifier: Modifier = Modifier, selectedUri: Uri) {
    Text(
        text = stringResource(id = R.string.selected_image_text, selectedUri),
        modifier = modifier.padding(16.dp),
        color = Color.DarkGray
    )
}

@Preview
@Composable
fun SelectedImagePreview() {
    SelectedImage(selectedUri = Uri.EMPTY)
}