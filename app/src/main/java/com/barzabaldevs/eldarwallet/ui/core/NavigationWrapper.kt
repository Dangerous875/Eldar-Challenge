package com.barzabaldevs.eldarwallet.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.barzabaldevs.eldarwallet.ui.core.navigation.NavigationRoutes.*
import com.barzabaldevs.eldarwallet.ui.core.viewmodel.NavigationWrapperViewModel
import com.barzabaldevs.eldarwallet.ui.screens.addCreditCardScreen.AddCreditCardScreen
import com.barzabaldevs.eldarwallet.ui.screens.loginScreen.LoginScreen
import com.barzabaldevs.eldarwallet.ui.screens.generateQRScreen.QRScreen
import com.barzabaldevs.eldarwallet.ui.screens.homeScreen.HomeScreen
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.MainScreen
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenViewModel
import com.barzabaldevs.eldarwallet.ui.screens.payScreen.PayScreen

@Composable
fun NavigationWrapper(viewModel: NavigationWrapperViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val auth by viewModel.auth.collectAsState()
    val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = HomeScreenRoute) {
        composable<HomeScreenRoute> {
            HomeScreen(
                auth,
                navigateToMainScreen = { navController.navigate(MainScreenRoute) },
                navigateToLoginScreen = { loginSelected ->
                    navController.navigate(LoginScreenRoute(loginSelected = loginSelected))
                })
        }
        composable<LoginScreenRoute> { navBackStackEntry ->
            val safeArgs = navBackStackEntry.toRoute<LoginScreenRoute>()
            LoginScreen(
                safeArgs.loginSelected,
                auth,
                navigateToMainScreen = { navController.navigate(MainScreenRoute) })
        }
        composable<MainScreenRoute> {
            MainScreen(
                auth,
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(HomeScreenRoute) { inclusive = true }
                    }
                },
                navigateToQRScreen = { navController.navigate(GenerateQRCodeRoute) },
                navigateToPayScreen = { navController.navigate(PayScreenRoute) },
                navigateToAddCreditCard = { navController.navigate(AddCreditCardRoute) },
                viewModel = mainScreenViewModel
            )
        }
        composable<GenerateQRCodeRoute> {
            QRScreen {
                navController.navigate(
                    MainScreenRoute
                ) {
                    popUpTo(MainScreenRoute) { inclusive = true }
                }
            }
        }
        composable<PayScreenRoute> {
            PayScreen(viewModel = mainScreenViewModel) {
                navController.navigate(
                    MainScreenRoute
                ) {
                    popUpTo(MainScreenRoute) { inclusive = true }
                }

            }
        }
        composable<AddCreditCardRoute> {
            AddCreditCardScreen(viewModel = mainScreenViewModel) {
                navController.navigate(
                    MainScreenRoute
                ) {
                    popUpTo(MainScreenRoute) { inclusive = true }
                }
            }
        }
    }
}