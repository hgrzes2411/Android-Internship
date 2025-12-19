package com.example.androidintern.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.PlaceholderRepository
import com.example.androidintern.data.Product
import com.example.androidintern.ui.models.ItemCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class AddPictureUiState(
    val expanded: Boolean = false,
    val selectedCategory: ItemCategory = ItemCategory.CATEGORY_1,
    val categories: List<ItemCategory> = ItemCategory.entries,
    val selectedUri: Uri? = null,
    val showCameraPreview: Boolean = false,
    val repositoryData: Product? = null,
    val isLoading: Boolean = false
)

class AddPictureViewModel(
    private val repository: PlaceholderRepository = PlaceholderRepository()
) : ViewModel() {

    private val _expanded = MutableStateFlow(false)
    private val _selectedCategory = MutableStateFlow(ItemCategory.CATEGORY_1)
    private val _selectedUri = MutableStateFlow<Uri?>(null)
    private val _showCameraPreview = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<AddPictureUiState> = combine(
        _expanded,
        _selectedCategory,
        _selectedUri,
        _showCameraPreview,
        _isLoading
    ) { expanded, selectedCategory, selectedUri, showCameraPreview, isLoading ->
        AddPictureUiState(
            expanded = expanded,
            selectedCategory = selectedCategory,
            selectedUri = selectedUri,
            showCameraPreview = showCameraPreview,
            isLoading = isLoading
        )
    }.combine(repository.getData()) { state, repositoryData ->
        state.copy(repositoryData = repositoryData)
    }.stateIn(
        scope = viewModelScope,
        // This is a performance optimization. It keeps the upstream flow active for 5 seconds
        // after the last collector stops listening. This is useful for preventing the flow
        // from being canceled and restarted during configuration changes (e.g., screen rotation).
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddPictureUiState()
    )

    fun onExpandedChange(isExpanded: Boolean) {
        _expanded.value = isExpanded
    }

    fun onPhotoSelected(uri: Uri?) {
        viewModelScope.launch {
            _isLoading.value = true
            _selectedUri.value = uri
            _showCameraPreview.value = false
            withContext(Dispatchers.IO) {
                delay(3000)
            }
            _isLoading.value = false
        }
    }

    fun onTakePhotoClick() {
        _showCameraPreview.value = true
    }

    fun onCameraDismiss() {
        _showCameraPreview.value = false
    }

    fun onCategorySelected(category: ItemCategory) {
        _selectedCategory.value = category
        _expanded.value = false
    }

    fun onDismiss() {
        _expanded.value = false
    }
}
