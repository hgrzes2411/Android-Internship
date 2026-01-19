package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.ProductsRepository
import com.example.androidintern.data.RemoteProductsRepository
import com.example.androidintern.data.model.Product
import com.example.androidintern.data.model.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StoreProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false
)

class StoreProductDetailsViewModel(
    private val productsRepository: ProductsRepository,
    private val remoteProductsRepository: RemoteProductsRepository,
    private val productId: Int
) : ViewModel() {

    val uiState: StateFlow<StoreProductDetailsUiState> = flow {
        emit(remoteProductsRepository.getProduct(productId).toDomainModel())
    }.map { StoreProductDetailsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StoreProductDetailsUiState(isLoading = true)
        )

    private var lastSaveTime = 0L

    fun saveProduct() {
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

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val productsRepository: ProductsRepository,
        private val remoteProductsRepository: RemoteProductsRepository,
        private val productId: Int
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoreProductDetailsViewModel::class.java)) {
                return StoreProductDetailsViewModel(
                    productsRepository,
                    remoteProductsRepository,
                    productId
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
