package com.example.androidintern.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.androidintern.ui.models.ItemCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddPictureUiState(
    val expanded: Boolean = false,
    val selectedCategory: ItemCategory = ItemCategory.CATEGORY_1,
    val categories: List<ItemCategory> = ItemCategory.values().toList(),
    val selectedUri: Uri? = null
)

class AddPictureViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddPictureUiState())
    val uiState = _uiState.asStateFlow()

    fun onExpandedChange(isExpanded: Boolean) {
        _uiState.update { it.copy(expanded = isExpanded) }
    }

    fun onPhotoSelected(uri: Uri?) {
        _uiState.update { it.copy(selectedUri = uri) }
    }

    fun onCategorySelected(category: ItemCategory) {
        _uiState.update {
            it.copy(
                selectedCategory = category,
                expanded = false
            )
        }
    }

    fun onDismiss() {
        _uiState.update { it.copy(expanded = false) }
    }
}
