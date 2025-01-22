package com.barzabaldevs.eldarwallet.ui.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes {

    @Serializable
    data class LoginScreenRoute(val login: Boolean) : NavigationRoutes()

    @Serializable
    data object GenerateQRCodeRoute : NavigationRoutes()

    @Serializable
    data object HomeScreenRoute : NavigationRoutes()

    @Serializable
    data object MainScreenRoute : NavigationRoutes()

}