package com.barzabaldevs.eldarwallet.ui.components.utils

fun formatDate(date: String): String {
    return when {
        date.length <= 2 -> date
        date.length > 4 -> date.take(4)
        else -> "${date.take(2)}/${date.drop(2)}"
    }
}