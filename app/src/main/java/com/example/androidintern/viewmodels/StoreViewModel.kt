package com.example.androidintern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.model.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _products.value = productsRepository.getProducts().map { product -> product.toDomainModel() }
            } catch (e: IOException) {
                _error.value = "No internet connection, please try again later."
            }
        }
    }
}
