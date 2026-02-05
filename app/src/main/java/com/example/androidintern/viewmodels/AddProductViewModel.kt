package com.example.androidintern.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.model.Category
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.ui.components.CategoryUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddProductUiState(
    val expanded: Boolean = false,
    val selectedCategory: CategoryUi = CategoryUi.CATEGORY1,
    val categories: List<CategoryUi> = CategoryUi.entries.toList(),
    val selectedUri: Uri? = null,
    val showCameraPreview: Boolean = false,
    val title: String = "",
    val description: String = ""
)

class AddProductViewModel(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()

    private val _isAddingProduct = MutableStateFlow(false)
    val isAddingProduct: StateFlow<Boolean> = _isAddingProduct.asStateFlow()

    private val _isSelectingPicture = MutableStateFlow(false)
    val isSelectingPicture: StateFlow<Boolean> = _isSelectingPicture.asStateFlow()

    fun onExpandedChange(isExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(expanded = isExpanded)
    }

    fun onPhotoSelected(uri: Uri?) {
        _uiState.value = _uiState.value.copy(showCameraPreview = false)

        viewModelScope.launch {
            _isSelectingPicture.value = true
            delay(3000) // Pretending to load data
            _uiState.value = _uiState.value.copy(selectedUri = uri)
            _isSelectingPicture.value = false
        }
    }

    fun onTakePhotoClick() {
        _uiState.value = _uiState.value.copy(showCameraPreview = true)
    }

    fun onCameraDismiss() {
        _uiState.value = _uiState.value.copy(showCameraPreview = false)
    }

    fun onCategorySelected(category: CategoryUi) {
        _uiState.value = _uiState.value.copy(selectedCategory = category, expanded = false)
    }

    fun onDismiss() {
        _uiState.value = _uiState.value.copy(expanded = false)
    }

    fun onTitleChanged(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun addProduct() = viewModelScope.launch {
        _isAddingProduct.value = true
        try {
            productsRepository.insertProduct(
                Product(
                    title = uiState.value.title,
                    description = uiState.value.description,
                    category = toCategory(uiState.value.selectedCategory),
                ),
                imageUri = uiState.value.selectedUri
            )
        } catch (e: Exception) {
            Log.e("ADD_PRODUCT", "Error inserting product", e)
        } finally {
            _isAddingProduct.value = false
        }
    }

    private fun toCategory(categoryUi: CategoryUi): Category {
        return when (categoryUi) {
            CategoryUi.CATEGORY1 -> Category.CATEGORY1
            CategoryUi.CATEGORY2 -> Category.CATEGORY2
            CategoryUi.CATEGORY3 -> Category.CATEGORY3
        }
    }

    companion object {
        fun Factory(repository: ProductsRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddProductViewModel(repository) as T
            }
        }
    }
}