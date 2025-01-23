package com.barzabaldevs.eldarwallet.ui.components

fun formatCardNumber(cardNumber: String): String {
    return cardNumber.chunked(4).joinToString(" ")
}