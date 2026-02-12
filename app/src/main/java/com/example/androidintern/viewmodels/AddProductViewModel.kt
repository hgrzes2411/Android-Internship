package com.example.androidintern.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.model.Category
import com.example.androidintern.ui.components.CategoryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddProductUiState(
    val selectedUri: Uri? = null,
    val title: String = "",
    val description: String = "",
    val expanded: Boolean = false,
    val selectedCategory: CategoryUi = CategoryUi.CATEGORY1,
    val categories: List<CategoryUi> = CategoryUi.entries.toList(),
    val isUploadingImage: Boolean = false
)

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()

    fun onPhotoSelected(uri: Uri?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUploadingImage = true)
            delay(3000) // Pretending to load data
            _uiState.value = _uiState.value.copy(selectedUri = uri, isUploadingImage = false)
        }
    }

    fun onTitleChanged(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onExpandedChange(isExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(expanded = isExpanded)
    }

    fun onCategorySelected(category: CategoryUi) {
        _uiState.value = _uiState.value.copy(selectedCategory = category, expanded = false)
    }

    fun onDismiss() {
        _uiState.value = _uiState.value.copy(expanded = false)
    }

    fun addProduct() {
        viewModelScope.launch {
            val product = Product(
                title = uiState.value.title,
                description = uiState.value.description,
                category = toCategory(uiState.value.selectedCategory)
            )
            productsRepository.insertProduct(product, uiState.value.selectedUri)
        }
    }

    private fun toCategory(categoryUi: CategoryUi): Category {
        return when (categoryUi) {
            CategoryUi.CATEGORY1 -> Category.CATEGORY1
            CategoryUi.CATEGORY2 -> Category.CATEGORY2
            CategoryUi.CATEGORY3 -> Category.CATEGORY3
        }
    }
}
