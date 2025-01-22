package com.barzabaldevs.eldarwallet.domain

import android.graphics.Bitmap
import com.barzabaldevs.eldarwallet.domain.model.UserModel

interface Repository {
    suspend fun insertUser(user: UserModel)
    suspend fun getUserById(id: String): UserModel

    suspend fun getQRCode(): Bitmap?
}