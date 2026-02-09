package com.example.androidintern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.ui.components.ProductItemUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProductListUiState(
    val products: List<ProductItemUiData> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    val uiState: StateFlow<ProductListUiState> = productsRepository.getAllProductsStream().map { products ->
        ProductListUiState(products.map { product ->
            ProductItemUiData(
                id = product.id.toString(),
                title = product.title,
                description = product.description,
                photoPath = product.photoPath ?: ""
            )
        })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProductListUiState(isLoading = true)
    )
}
