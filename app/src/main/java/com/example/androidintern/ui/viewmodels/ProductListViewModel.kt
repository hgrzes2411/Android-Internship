package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.androidintern.R
import com.example.androidintern.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()
    private var lastClickTime = 0L

    init {
        _uiState.value = ProductListUiState(
            products = listOf(
                Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ),
                Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ),
                Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ),
                Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ),
                Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                )
            )
        )
    }

    fun onAddClickDebounced(onAddClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > DEBOUNCE_INTERVAL_SHORT) {
            lastClickTime = currentTime
            onAddClick()
        }
    }

    companion object {
        private const val DEBOUNCE_INTERVAL_SHORT = 500L
    }
}
