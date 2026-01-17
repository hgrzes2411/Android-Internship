package com.example.androidintern.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidintern.data.RemoteProductsRepository

class StoreViewModelFactory(private val remoteProductsRepository: RemoteProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoreViewModel(remoteProductsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
