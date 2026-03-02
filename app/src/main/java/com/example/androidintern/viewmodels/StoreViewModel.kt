package com.example.androidintern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.ProductsRepository
import com.example.androidintern.datastore.model.Product
import com.example.androidintern.datastore.model.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class StoreViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showRefreshPrompt = MutableStateFlow(false)
    val showRefreshPrompt: StateFlow<Boolean> = _showRefreshPrompt.asStateFlow()

    private var refreshTimerJob: Job? = null
    private var currentPage = 1
    private var isLastPage = false

    init {
        loadProducts()
    }

    fun onRefresh() {
        currentPage = 1
        isLastPage = false
        _products.value = emptyList()
        loadProducts()
    }

    fun onDismissRefreshPrompt() {
        _showRefreshPrompt.value = false
    }

    fun loadMoreProducts() {
        if (isLoading.value || isLastPage) return
        loadProducts(isInitialLoad = false)
    }

    private fun loadProducts(isInitialLoad: Boolean = true) {
        if (isInitialLoad) {
            currentPage = 1
            isLastPage = false
            _products.value = emptyList()
        }

        refreshTimerJob?.cancel()
        _showRefreshPrompt.value = false
        _isLoading.value = true

        viewModelScope.launch {
            try {
                delay(2000)
                val newProducts = productsRepository.getProducts(currentPage, PAGE_SIZE).map { it.toDomainModel() }
                if (newProducts.isEmpty()) {
                    isLastPage = true
                } else {
                    _products.value = _products.value + newProducts
                    currentPage++
                }
                _error.value = null
            } catch (e: IOException) {
                _error.value = "No internet connection, please try again later."
            } finally {
                _isLoading.value = false
                if (isInitialLoad) {
                    startRefreshTimer()
                }
            }
        }
    }

    private fun startRefreshTimer() {
        refreshTimerJob = viewModelScope.launch {
            delay(30000)
            _showRefreshPrompt.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        refreshTimerJob?.cancel()
    }
}
