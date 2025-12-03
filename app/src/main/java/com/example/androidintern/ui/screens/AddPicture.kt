package com.example.androidintern.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidintern.ui.elements.TitleBar
import com.example.androidintern.ui.intermediate.SelectedImage
import com.example.androidintern.ui.intermediate.UploadBox
import com.example.androidintern.ui.intermediate.rememberPhotoSelector
import com.example.androidintern.ui.theme.AndroidInternTheme
import com.example.androidintern.ui.theme.AppBackgroundColor

@Composable
fun AddPictureScreen(
    modifier: Modifier = Modifier
) {
    var selectedUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val onUploadClick = rememberPhotoSelector { uri ->
        selectedUri = uri
    }
    AddPictureContent(
        modifier = modifier,
        selectedUri = selectedUri,
        onUploadClick = onUploadClick
    )
}

@Composable
private fun AddPictureContent(
    modifier: Modifier = Modifier,
    selectedUri: Uri?,
    onUploadClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar()
        Spacer(modifier = Modifier.height(150.dp))
        if (selectedUri == null) {
            UploadBox(onUploadClick = onUploadClick)
        } else {
            SelectedImage(selectedUri = selectedUri)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AndroidInternTheme {
        AddPictureContent(selectedUri = null, onUploadClick = {})
    }
}
