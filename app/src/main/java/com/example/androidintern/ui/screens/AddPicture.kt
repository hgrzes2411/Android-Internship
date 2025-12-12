package com.example.androidintern.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidintern.ui.elements.CategoryDropDown
import com.example.androidintern.ui.elements.DescriptionInputField
import com.example.androidintern.ui.elements.TitleBar
import com.example.androidintern.ui.elements.TitleInputBox
import com.example.androidintern.ui.intermediate.SelectedImage
import com.example.androidintern.ui.intermediate.UploadBox
import com.example.androidintern.ui.models.ItemCategory
import com.example.androidintern.ui.theme.AndroidInternTheme
import com.example.androidintern.ui.viewmodels.AddPictureViewModel

@Composable
fun AddPictureScreen(
    modifier: Modifier = Modifier,
    viewModel: AddPictureViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AddPictureContent(
        modifier = modifier,
        selectedUri = uiState.selectedUri,
        onImageSelected = { viewModel.onPhotoSelected(it) },
        expanded = uiState.expanded,
        selectedCategory = uiState.selectedCategory,
        categories = uiState.categories,
        onExpandedChange = viewModel::onExpandedChange,
        onCategorySelected = viewModel::onCategorySelected,
        onDismiss = viewModel::onDismiss
    )
}

@Composable
private fun AddPictureContent(
    modifier: Modifier = Modifier,
    selectedUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    expanded: Boolean,
    selectedCategory: ItemCategory,
    categories: List<ItemCategory>,
    onExpandedChange: (Boolean) -> Unit,
    onCategorySelected: (ItemCategory) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar()
        Spacer(modifier = Modifier.height(150.dp))
        if (selectedUri == null) {
            UploadBox(onImageSelected = onImageSelected)
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
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AndroidInternTheme {
        AddPictureContent(
            modifier = Modifier,
            selectedUri = null,
            onImageSelected = {},
            expanded = false,
            selectedCategory = ItemCategory.CATEGORY_1,
            categories = ItemCategory.values().toList(),
            onExpandedChange = {},
            onCategorySelected = {},
            onDismiss = {}
        )
    }
}
