package com.example.androidintern.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.model.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val isRemote: Boolean = false,
    val isSaving: Boolean = false
)

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState(isLoading = true))
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        val productId: Int = savedStateHandle.get<String>("productId")!!.toInt()
        val isRemote: Boolean = savedStateHandle.get<Boolean>("isRemote") ?: false
        _uiState.update { it.copy(isRemote = isRemote) }

        viewModelScope.launch {
            if (isRemote) {
                try {
                    val product = productsRepository.getProduct(productId).toDomainModel()
                    _uiState.update { it.copy(product = product, isLoading = false) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } else {
                productsRepository.getProductStream(productId)
                    .collect { product ->
                        _uiState.update { it.copy(product = product, isLoading = false) }
                    }
            }
        }
    }

    fun saveProduct() {
        viewModelScope.launch {
            if (_uiState.value.isSaving) return@launch

            _uiState.update { it.copy(isSaving = true) }
            try {
                uiState.value.product?.let { productToSave ->
                    productsRepository.insertProduct(productToSave.copy(id = 0))
                }
            } finally {
                delay(1000)
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
