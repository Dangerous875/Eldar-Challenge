package com.barzabaldevs.eldarwallet.ui.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes {

    @Serializable
    data object LoginScreenRoute : NavigationRoutes()

    @Serializable
    data object GenerateQRCodeRoute : NavigationRoutes()
}