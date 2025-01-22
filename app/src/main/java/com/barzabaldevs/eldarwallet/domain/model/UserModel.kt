package com.barzabaldevs.eldarwallet.domain.model

import com.barzabaldevs.eldarwallet.data.database.entities.UserDataEntity

data class UserModel(
    val id: String,
    val email: String,
    val name: String,
    val lastName: String,
    val balance: Double = 500000.0,
    val creditCards: List<CreditCard> = emptyList()
) {
    fun toDataBase(encryptCards: String): UserDataEntity {
        return UserDataEntity(
            id = id,
            email = email,
            name = name,
            lastName = lastName,
            balance = balance,
            creditCards = encryptCards
        )
    }
}
