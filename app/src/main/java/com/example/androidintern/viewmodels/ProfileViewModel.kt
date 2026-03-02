package com.example.androidintern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.datastore.UserRepository
import com.example.androidintern.datastore.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginLoading: Boolean = false,
    val loginError: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.userId.collectLatest { userId ->
                if (userId != null) {
                    fetchUser(userId)
                } else {
                    _uiState.value = _uiState.value.copy(user = null)
                }
            }
        }
    }

    private fun fetchUser(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            userRepository.getUser(id).fold(
                onSuccess = { user ->
                    _uiState.value = _uiState.value.copy(user = user, isLoading = false)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = error.message)
                }
            )
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoginLoading = true, loginError = null)
            userRepository.login(username, password).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(isLoginLoading = false)
                    onSuccess()
                },
                onFailure = { error ->
                    val errorMessage = if (error is HttpException && error.code() == 400) {
                        "Incorrect email or password"
                    } else {
                        error.message
                    }
                    _uiState.value = _uiState.value.copy(isLoginLoading = false, loginError = errorMessage)
                }
            )
        }
    }

    fun onDismissLoginError() {
        _uiState.value = _uiState.value.copy(loginError = null)
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
