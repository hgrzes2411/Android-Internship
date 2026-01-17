package com.example.androidintern.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidintern.di.AppContainer
import com.example.androidintern.ui.screens.AddProductScreen
import com.example.androidintern.ui.screens.ProductDetailsScreen
import com.example.androidintern.ui.screens.ProductListScreen
import com.example.androidintern.ui.screens.StoreProductDetailsScreen
import com.example.androidintern.ui.screens.StoreScreen
import com.example.androidintern.ui.viewmodels.StoreViewModelFactory

@Composable
fun NavGraph(navController: NavHostController, appContainer: AppContainer) {
    NavHost(navController = navController, startDestination = Routes.PRODUCT_LIST) {
        composable(Routes.PRODUCT_LIST) {
            Log.d("NavGraph", "Navigating to ProductListScreen")
            ProductListScreen(onAddClick = {
                navController.navigate(Routes.ADD_PRODUCT) {
                    launchSingleTop = true
                }
            }, onProductClick = {
                navController.navigate(Routes.productDetails(it))
            }, onStoreClick = {
                navController.navigate(Routes.STORE)
            })
        }
        composable(Routes.ADD_PRODUCT) {
            Log.d("NavGraph", "Navigating to AddProduct")
            AddProductScreen(navController = navController)
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
        composable(Routes.STORE) {
            StoreScreen(
                viewModel = viewModel(
                    factory = StoreViewModelFactory(appContainer.remoteProductsRepository)
                )
            ) { productId ->
                navController.navigate(Routes.storeProductDetails(productId))
            }
        }
        composable(
            route = Routes.STORE_PRODUCT_DETAILS,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            if (productId != null) {
                StoreProductDetailsScreen(productId = productId)
            }
        }
    }
}
