package com.barzabaldevs.eldarwallet.ui.components.utils

import com.barzabaldevs.eldarwallet.R

fun getCardIcon(cardNumber: String): Int {
    return when {
        cardNumber.startsWith("4") -> R.drawable.ic_visa
        cardNumber.startsWith("5") -> R.drawable.ic_master
        cardNumber.startsWith("3") -> R.drawable.ic_american
        else -> R.drawable.ic_default_card
    }
}