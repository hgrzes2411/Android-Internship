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

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
)

class ProductListViewModel(
    productsRepository: ProductsRepository
) : ViewModel() {

    val uiState: StateFlow<ProductListUiState> =
        productsRepository.getAllProductsStream().map { ProductListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ProductListUiState(isLoading = true)
            )

    companion object {
        fun Factory(repository: ProductsRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductListViewModel(repository) as T
            }
        }
    }
}
