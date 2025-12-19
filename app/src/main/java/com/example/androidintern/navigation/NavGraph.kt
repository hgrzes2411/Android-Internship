package com.example.androidintern.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidintern.ui.screens.AddPictureScreen
import com.example.androidintern.ui.screens.ProductDetailsScreen
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
            }, onProductClick = {
                navController.navigate(Routes.productDetails(it))
            })
        }
        composable(Routes.ADD_PICTURE) {
            Log.d("NavGraph", "Navigating to AddPicture")
            AddPictureScreen()
        }
        composable(
            route = Routes.PRODUCT_DETAILS,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            if (productId != null) {
                ProductDetailsScreen(productId = productId)
            }
        }
    }
}