package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.RemoteProductsRepository
import com.example.androidintern.data.model.Product
import com.example.androidintern.data.model.toDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreViewModel(private val remoteProductsRepository: RemoteProductsRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        viewModelScope.launch {
            _products.value = remoteProductsRepository.getProducts().map { it.toDomainModel() }
        }
    }
}
