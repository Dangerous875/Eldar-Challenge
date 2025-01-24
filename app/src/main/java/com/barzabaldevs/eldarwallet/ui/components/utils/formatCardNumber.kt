package com.barzabaldevs.eldarwallet.ui.components.utils

fun formatCardNumber(cardNumber: String): String {
    return cardNumber.chunked(4).joinToString(" ")
}