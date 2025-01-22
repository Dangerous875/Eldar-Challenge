package com.barzabaldevs.eldarwallet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreditCard(
    val cardNumber: String,
    val securityCode: String,
    val expirationDate: String,
    val name: String,
    val lastName: String
)
