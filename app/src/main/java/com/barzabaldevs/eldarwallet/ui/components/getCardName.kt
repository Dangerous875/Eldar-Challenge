package com.barzabaldevs.eldarwallet.ui.components

fun getCardName(cardNumber: String): String {
    return when {
        cardNumber.startsWith("4") -> "Visa"
        cardNumber.startsWith("5") -> "Master Card"
        cardNumber.startsWith("3") -> "American Express"
        else -> "Maestro"
    }
}