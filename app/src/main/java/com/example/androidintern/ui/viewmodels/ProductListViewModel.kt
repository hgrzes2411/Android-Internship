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

    init {
        // Load initial data
        _uiState.value = ProductListUiState(
            products = listOf(
                Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ), Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ), Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ), Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                ), Product(
                    R.drawable.ic_launcher_background,
                    "Title",
                    "Description duis aute irure dolor in reprehenderit in voluptate velit."
                )
            )
        )
    }
}
