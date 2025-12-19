package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
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

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true
)

class ProductListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private var lastClickTime = 0L

    init {
        viewModelScope.launch {
            val loadedProducts = withContext(Dispatchers.IO) {
                delay(3000)
                SampleData.products
            }
            _uiState.value = ProductListUiState(products = loadedProducts, isLoading = false)
        }
    }

    fun onAddClickDebounced(onAddClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > 500L) { // 500ms debounce window
            lastClickTime = currentTime
            onAddClick()
        }
    }
}
