package com.example.androidintern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.model.toDomainModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false
)

class ProductDetailsViewModel(
    private val productsRepository: ProductsRepository,
    private val productId: Int,
    private val isRemote: Boolean
) : ViewModel() {

    val uiState: StateFlow<ProductDetailsUiState>

    init {
        uiState = if (isRemote) {
            flow {
                emit(productsRepository.getProduct(productId).toDomainModel())
            }.map { ProductDetailsUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = ProductDetailsUiState(isLoading = true)
                )
        } else {
            productsRepository.getProductStream(productId).map { ProductDetailsUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = ProductDetailsUiState(isLoading = true)
                )
        }
    }

    private var lastSaveTime = 0L

    fun saveProduct() {
        if (isRemote) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastSaveTime > 2000L) {
                lastSaveTime = currentTime
                viewModelScope.launch {
                    uiState.value.product?.let {
                        productsRepository.insertProduct(it.copy(id = 0))
                    }
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            productsRepository: ProductsRepository,
            productId: Int,
            isRemote: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
                    return ProductDetailsViewModel(
                        productsRepository,
                        productId,
                        isRemote
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
