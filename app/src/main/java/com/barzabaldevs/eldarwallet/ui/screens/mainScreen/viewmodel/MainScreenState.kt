package com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel

import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.model.UserModel

data class MainScreenState(
    val isLoading: Boolean = true,
    val userData : UserModel? = null,
    val creditCards: List<CreditCard> = emptyList(),
    val cardSelected : CreditCard? = null
)
