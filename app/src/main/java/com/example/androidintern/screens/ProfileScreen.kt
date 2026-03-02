package com.example.androidintern.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.androidintern.datastore.model.User
import com.example.androidintern.ui.R
import com.example.androidintern.ui.components.Banner
import com.example.androidintern.ui.components.SignInButton
import com.example.androidintern.ui.styles.SignOutButtonColor
import com.example.androidintern.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSignInClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Banner(title = stringResource(id = R.string.account_title))
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.user != null -> {
                    ProfileContent(
                        user = uiState.user!!,
                        onSignOutClick = { viewModel.logout() }
                    )
                }
                else -> {
                    SignInButton(
                        text = stringResource(id = R.string.sign_in),
                        onClick = onSignInClick,
                        modifier = Modifier.width(92.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileContent(user: User, onSignOutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (user.image.isNotEmpty()) {
                AsyncImage(
                    model = user.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, CircleShape)
                )
            } else {
                Text(
                    text = user.firstName.take(1) + user.lastName.take(1),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp,
                        color = Color.DarkGray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "${user.firstName} ${user.lastName}",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = onSignOutClick,
            modifier = Modifier
                .width(112.dp)
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SignOutButtonColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sign_out),
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInBottomSheet(
    onDismissRequest: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(32.dp)
                    .height(4.dp)
                    .background(Color.Gray, RoundedCornerShape(2.dp))
            )
        }
    ) {
        SignInContent(
            isLoading = uiState.isLoginLoading,
            onSignInClick = { username, password ->
                viewModel.login(username, password) {
                    onDismissRequest()
                }
            }
        )

        if (uiState.loginError != null) {
            AlertDialog(
                onDismissRequest = { viewModel.onDismissLoginError() },
                title = { Text(text = stringResource(id = R.string.error_dialog_title)) },
                text = { Text(text = uiState.loginError!!) },
                confirmButton = {
                    Button(onClick = { viewModel.onDismissLoginError() }) {
                        Text(text = stringResource(id = R.string.ok_button_text))
                    }
                }
            )
        }
    }
}

@Composable
fun SignInContent(
    isLoading: Boolean,
    onSignInClick: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("emilys") } // Default from dummyjson
    var password by remember { mutableStateOf("emilyspass") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp, start = 32.dp, end = 32.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.email_label)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        SignInButton(
            text = stringResource(id = R.string.sign_in),
            onClick = { onSignInClick(username, password) },
            isLoading = isLoading,
            modifier = Modifier.width(165.dp)
        )
    }
}
