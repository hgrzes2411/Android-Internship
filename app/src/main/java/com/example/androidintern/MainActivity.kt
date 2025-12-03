package com.example.androidintern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.androidintern.navigation.NavGraph
import com.example.androidintern.ui.theme.AndroidInternTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidInternTheme {
                NavGraph()
            }
        }
    }
}