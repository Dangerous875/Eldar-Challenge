package com.barzabaldevs.eldarwallet.ui.components.utils

import com.barzabaldevs.eldarwallet.domain.model.UserModel
import java.util.Locale

fun validName(firstName: String, lastName: String, userData: UserModel): Boolean {
    val nameRegister = userData.name.trim().uppercase(Locale.getDefault())
    val lastNameRegister = userData.lastName.trim().uppercase(Locale.getDefault())

    return firstName.trim().uppercase(Locale.getDefault()) == nameRegister &&
            lastName.trim().uppercase(Locale.getDefault()) == lastNameRegister
}