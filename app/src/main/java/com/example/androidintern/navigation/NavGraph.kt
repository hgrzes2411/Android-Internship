package com.example.androidintern.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidintern.ui.screens.AddPictureScreen
import com.example.androidintern.ui.screens.ProductListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.PRODUCT_LIST) {
        composable(Routes.PRODUCT_LIST) {
            Log.d("NavGraph", "Navigating to ProductListScreen")
            ProductListScreen(onAddClick = {
                navController.navigate(Routes.ADD_PICTURE) {
                    launchSingleTop = true
                }
            })
        }
        composable(Routes.ADD_PICTURE) {
            Log.d("NavGraph", "Navigating to AddPicture")
            AddPictureScreen()
        }
    }
}