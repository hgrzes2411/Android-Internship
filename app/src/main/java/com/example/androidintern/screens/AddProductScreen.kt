package com.example.androidintern.screens

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidintern.di.AppContainer
import com.example.androidintern.navigation.Routes
import com.example.androidintern.ui.R
import com.example.androidintern.ui.components.AddItemButton
import com.example.androidintern.ui.components.CategoryDropdown
import com.example.androidintern.ui.components.CategoryUi
import com.example.androidintern.ui.components.CircularIndicator
import com.example.androidintern.ui.components.DescriptionInputField
import com.example.androidintern.ui.components.SelectedImage
import com.example.androidintern.ui.components.TitleBar
import com.example.androidintern.ui.components.TitleInputBox
import com.example.androidintern.ui.components.UploadBox
import com.example.androidintern.ui.themes.AndroidInternTheme
import com.example.androidintern.viewmodels.AddProductViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    addProductViewModel: AddProductViewModel = viewModel(
        factory = AddProductViewModel.Factory(
            AppContainer(LocalContext.current).productsRepository
        )
    ),
    navController: NavController
) {
    val uiState by addProductViewModel.uiState.collectAsState()
    val isAddingProduct by addProductViewModel.isAddingProduct.collectAsState()
    val isSelectingPicture by addProductViewModel.isSelectingPicture.collectAsState()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (uiState.showCameraPreview) {
        LaunchedEffect(cameraPermissionState) {
            if (!cameraPermissionState.status.isGranted) {
                cameraPermissionState.launchPermissionRequest()
            }
        }

        if (cameraPermissionState.status.isGranted) {
            CameraPreviewScreen(
                onImageCaptured = { addProductViewModel.onPhotoSelected(it) },
                onDismiss = { addProductViewModel.onCameraDismiss() })
        } else {
            PermissionRequestDialog(
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() },
                onDismiss = { addProductViewModel.onCameraDismiss() }
            )
        }
    } else {
        AddProductContent(
            modifier = modifier,
            selectedUri = uiState.selectedUri,
            onImageSelected = { addProductViewModel.onPhotoSelected(it) },
            onTakePhotoClick = { addProductViewModel.onTakePhotoClick() },
            expanded = uiState.expanded,
            selectedCategory = uiState.selectedCategory,
            categories = uiState.categories,
            onExpandedChange = addProductViewModel::onExpandedChange,
            onCategorySelected = addProductViewModel::onCategorySelected,
            onDismiss = addProductViewModel::onDismiss,
            isLoading = isAddingProduct || isSelectingPicture,
            onAddItemClick = {
                addProductViewModel.addProduct()
                navController.navigate(Routes.PRODUCT_LIST)
            },
            title = uiState.title,
            onTitleChange = { addProductViewModel.onTitleChanged(it) },
            description = uiState.description,
            onDescriptionChange = { addProductViewModel.onDescriptionChanged(it) }
        )
    }
}

@Composable
private fun AddProductContent(
    modifier: Modifier = Modifier,
    selectedUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onTakePhotoClick: () -> Unit,
    expanded: Boolean,
    selectedCategory: CategoryUi,
    categories: List<CategoryUi>,
    onExpandedChange: (Boolean) -> Unit,
    onCategorySelected: (CategoryUi) -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean,
    onAddItemClick: () -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleBar()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.add_product_image_spacer_height)))
            if (selectedUri == null) {
                UploadBox(
                    onImageSelected = onImageSelected,
                    onTakePhotoClick = onTakePhotoClick
                )
            } else {
                SelectedImage(selectedUri = selectedUri)
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_large)))
            TitleInputBox(value = title, onValueChange = onTitleChange)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_medium)))
            CategoryDropdown(
                expanded = expanded,
                selectedCategory = selectedCategory,
                categories = categories,
                onExpandedChange = onExpandedChange,
                onCategorySelected = onCategorySelected,
                onDismiss = onDismiss
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_medium)))
            DescriptionInputField(value = description, onValueChange = onDescriptionChange)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_large)))
            AddItemButton(onClick = onAddItemClick, isLoading = isLoading)
        }
        if (isLoading) {
            CircularIndicator()
        }
    }
}

@Composable
private fun PermissionRequestDialog(
    onRequestPermission: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_large))) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.permission_request_dialog_text))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_medium)))
            Button(onClick = onRequestPermission) {
                Text(stringResource(R.string.permission_request_dialog_button))
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.permission_request_dialog_dismiss_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AndroidInternTheme {
        AddProductContent(
            modifier = Modifier,
            selectedUri = null,
            onImageSelected = {},
            onTakePhotoClick = {},
            expanded = false,
            selectedCategory = CategoryUi.CATEGORY1,
            categories = CategoryUi.entries.toList(),
            onExpandedChange = {},
            onCategorySelected = {},
            onDismiss = {},
            isLoading = false,
            onAddItemClick = {},
            title = "",
            onTitleChange = {},
            description = "",
            onDescriptionChange = {}
        )
    }
}
