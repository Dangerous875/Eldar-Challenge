package com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel

import com.barzabaldevs.eldarwallet.domain.model.UserModel

data class MainScreenState(
    val isLoading: Boolean = true,
    val userData : UserModel? = null
)
