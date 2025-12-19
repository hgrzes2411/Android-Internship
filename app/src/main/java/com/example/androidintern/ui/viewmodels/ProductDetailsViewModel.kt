package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.Product
import com.example.androidintern.data.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = true
)

class ProductDetailsViewModel(productId: String) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val foundProduct = withContext(Dispatchers.IO) {
                delay(2000)
                SampleData.products.find { it.id == productId }
            }
            _uiState.value = ProductDetailsUiState(product = foundProduct, isLoading = false)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val productId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductDetailsViewModel(productId) as T
        }
    }
}