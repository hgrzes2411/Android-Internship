package com.example.androidintern.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.example.androidintern.screens.AddProductScreen
import com.example.androidintern.screens.ProductDetailsScreen
import com.example.androidintern.screens.ProductListScreen
import com.example.androidintern.screens.ProfileScreen
import com.example.androidintern.screens.SignInBottomSheet
import com.example.androidintern.screens.StoreProductDetailsScreen
import com.example.androidintern.screens.StoreScreen
import com.example.androidintern.ui.navigation.Routes

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, 
        startDestination = Routes.PRODUCT_LIST,
        modifier = modifier
    ) {
        composable(Routes.PRODUCT_LIST) {
            Log.d("NavGraph", "Navigating to ProductListScreen")
            ProductListScreen(
                onAddClick = {
                    navController.navigate(Routes.ADD_PRODUCT) {
                        launchSingleTop = true
                    }
                }, 
                onProductClick = { productId ->
                    navController.navigate(Routes.productDetails(productId, isRemote = false))
                }, 
                onStoreClick = {
                    navController.navigate(Routes.STORE)
                },
                viewModel = hiltViewModel()
            )
        }
        composable(Routes.ADD_PRODUCT) {
            Log.d("NavGraph", "Navigating to AddProduct")
            AddProductScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(
            route = Routes.PRODUCT_DETAILS,
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType },
                navArgument("isRemote") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            ProductDetailsScreen(viewModel = hiltViewModel())
        }
        composable(Routes.STORE) {
            StoreScreen(viewModel = hiltViewModel()) { productId ->
                navController.navigate(Routes.productDetails(productId, isRemote = true))
            }
        }
        composable(
            route = Routes.STORE_PRODUCT_DETAILS,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            StoreProductDetailsScreen(viewModel = hiltViewModel())
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onSignInClick = {
                    navController.navigate(Routes.SIGN_IN_MODAL)
                }
            )
        }
        dialog(Routes.SIGN_IN_MODAL) {
            SignInBottomSheet(
                onDismissRequest = {
                    navController.popBackStack()
                }
            )
        }
    }
}
