package com.barzabaldevs.eldarwallet.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barzabaldevs.eldarwallet.ui.core.navigation.NavigationRoutes.*
import com.barzabaldevs.eldarwallet.ui.screens.generateQRScreen.QRScreen
import com.barzabaldevs.eldarwallet.ui.screens.loginScreen.LoginScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreenRoute) {
        composable<LoginScreenRoute> { LoginScreen { navController.navigate(GenerateQRCodeRoute) } }
        composable<GenerateQRCodeRoute> { QRScreen() }
    }
}