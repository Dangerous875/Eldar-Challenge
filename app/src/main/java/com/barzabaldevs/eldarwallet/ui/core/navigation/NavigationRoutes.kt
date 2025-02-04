package com.barzabaldevs.eldarwallet.ui.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes {

    @Serializable
    data class LoginScreenRoute(val loginSelected: Boolean) : NavigationRoutes()

    @Serializable
    data object HomeScreenRoute : NavigationRoutes()

    @Serializable
    data object MainScreenRoute : NavigationRoutes()

    @Serializable
    data object PayScreenRoute : NavigationRoutes()

    @Serializable
    data object GenerateQRCodeRoute : NavigationRoutes()

    @Serializable
    data object AddCreditCardRoute : NavigationRoutes()


}