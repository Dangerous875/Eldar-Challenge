package com.barzabaldevs.eldarwallet.domain

import android.graphics.Bitmap

interface Repository {
    suspend fun getQRCode(): Bitmap?
}