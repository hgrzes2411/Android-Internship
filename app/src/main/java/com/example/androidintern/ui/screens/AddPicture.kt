package com.example.androidintern.ui.screens

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidintern.ui.elements.CategoryDropDown
import com.example.androidintern.ui.elements.DescriptionInputField
import com.example.androidintern.ui.elements.IndeterminateCircularIndicator
import com.example.androidintern.ui.elements.TitleBar
import com.example.androidintern.ui.elements.TitleInputBox
import com.example.androidintern.ui.intermediate.SelectedImage
import com.example.androidintern.ui.intermediate.UploadBox
import com.example.androidintern.ui.models.ItemCategory
import com.example.androidintern.ui.theme.AndroidInternTheme
import com.example.androidintern.ui.viewmodels.AddPictureViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddPictureScreen(
    modifier: Modifier = Modifier,
    viewModel: AddPictureViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (uiState.showCameraPreview) {
        LaunchedEffect(cameraPermissionState) {
            if (!cameraPermissionState.status.isGranted) {
                cameraPermissionState.launchPermissionRequest()
            }
        }

        if (cameraPermissionState.status.isGranted) {
            CameraPreviewScreen(
                onImageCaptured = { viewModel.onPhotoSelected(it) },
                onDismiss = { viewModel.onCameraDismiss() }
            )
        } else {
            PermissionRequestDialog(
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() },
                onDismiss = { viewModel.onCameraDismiss() }
            )
        }
    } else {
        AddPictureContent(
            modifier = modifier,
            selectedUri = uiState.selectedUri,
            onImageSelected = { viewModel.onPhotoSelected(it) },
            onTakePhotoClick = { viewModel.onTakePhotoClick() },
            expanded = uiState.expanded,
            selectedCategory = uiState.selectedCategory,
            categories = uiState.categories,
            onExpandedChange = viewModel::onExpandedChange,
            onCategorySelected = viewModel::onCategorySelected,
            onDismiss = viewModel::onDismiss,
            isLoading = uiState.isLoading
        )
    }
}

@Composable
private fun AddPictureContent(
    modifier: Modifier = Modifier,
    selectedUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onTakePhotoClick: () -> Unit,
    expanded: Boolean,
    selectedCategory: ItemCategory,
    categories: List<ItemCategory>,
    onExpandedChange: (Boolean) -> Unit,
    onCategorySelected: (ItemCategory) -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleBar()
            Spacer(modifier = Modifier.height(150.dp))
            if (selectedUri == null) {
                UploadBox(
                    onImageSelected = onImageSelected,
                    onTakePhotoClick = onTakePhotoClick
                )
            } else {
                SelectedImage(selectedUri = selectedUri)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TitleInputBox()
            Spacer(modifier = Modifier.height(16.dp))
            CategoryDropDown(
                expanded = expanded,
                selectedCategory = selectedCategory,
                categories = categories,
                onExpandedChange = onExpandedChange,
                onCategorySelected = onCategorySelected,
                onDismiss = onDismiss
            )
            Spacer(modifier = Modifier.height(16.dp))
            DescriptionInputField()
        }
        if (isLoading) {
            IndeterminateCircularIndicator()
        }
    }
}

@Composable
private fun PermissionRequestDialog(
    onRequestPermission: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Camera permission is required to take photos.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRequestPermission) {
                Text("Request Permission")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AndroidInternTheme {
        AddPictureContent(
            modifier = Modifier,
            selectedUri = null,
            onImageSelected = {},
            onTakePhotoClick = {},
            expanded = false,
            selectedCategory = ItemCategory.CATEGORY_1,
            categories = ItemCategory.entries.toList(),
            onExpandedChange = {},
            onCategorySelected = {},
            onDismiss = {},
            isLoading = true
        )
    }
}
