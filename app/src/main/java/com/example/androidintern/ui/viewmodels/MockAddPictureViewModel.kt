package com.example.androidintern.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class MockAddPictureViewModel : ViewModel() {
    open var expanded by mutableStateOf(false)
    open var selectedCategory by mutableStateOf("Cars")

    open fun onExpandedChange() {}

    open fun onCategorySelected(category: String) {}

    open fun setCategories(categories: Array<String>) {}
    open fun onDismiss() {}
}