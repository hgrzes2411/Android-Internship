package com.example.androidintern.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidintern.datastore.model.Category

open class MockAddPictureViewModel : ViewModel() {
    open var expanded by mutableStateOf(false)
    open var selectedCategory by mutableStateOf(Category.CATEGORY1)

    open fun onExpandedChange() {}

    open fun onCategorySelected(category: Category) {}

    open fun setCategories(categories: List<Category>) {}
    open fun onDismiss() {}
}