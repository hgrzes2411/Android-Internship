package com.example.androidintern
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.androidintern.ui.AddPicture
import com.example.androidintern.ui.rememberPhotoSelector
import com.example.androidintern.ui.theme.AndroidInternTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidInternTheme {
                var selectedUri by remember { mutableStateOf<Uri?>(null) }
                val onUploadClick = rememberPhotoSelector { uri ->
                    selectedUri = uri
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    AddPicture(
                        modifier = Modifier.padding(padding),
                        selectedUri = selectedUri,
                        onUploadClick = onUploadClick
                    )
                }
            }
        }
    }
}