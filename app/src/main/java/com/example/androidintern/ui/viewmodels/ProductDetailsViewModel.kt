package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.ProductsRepository
import com.example.androidintern.data.model.Product
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false
)

class ProductDetailsViewModel(
    private val productsRepository: ProductsRepository,
    private val productId: Int
) : ViewModel() {

    val uiState: StateFlow<ProductDetailsUiState> =
        productsRepository.getProductStream(productId).map { ProductDetailsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ProductDetailsUiState(isLoading = true)
            )

    @Suppress("UNCHECKED_CAST")
    class Factory(private val productsRepository: ProductsRepository, private val productId: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
                return ProductDetailsViewModel(productsRepository, productId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
