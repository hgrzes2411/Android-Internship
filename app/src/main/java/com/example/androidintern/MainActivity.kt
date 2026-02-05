package com.example.androidintern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.androidintern.di.AppContainer
import com.example.androidintern.navigation.NavGraph
import com.example.androidintern.ui.themes.AndroidInternTheme

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
        enableEdgeToEdge()
        setContent {
            AndroidInternTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, appContainer = appContainer)
            }
        }
    }
}
