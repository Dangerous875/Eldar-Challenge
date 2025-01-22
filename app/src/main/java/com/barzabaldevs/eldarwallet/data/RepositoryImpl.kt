package com.barzabaldevs.eldarwallet.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.barzabaldevs.eldarwallet.data.remote.service.ApiServiceQR
import com.barzabaldevs.eldarwallet.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiServiceQR: ApiServiceQR) : Repository {
    override suspend fun getQRCode(): Bitmap? {
        return withContext(Dispatchers.IO) {
            val response = apiServiceQR.generateQRCode()
            if (response.isSuccessful) {
                response.body()?.byteStream()?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
            } else {
                null
            }
        }
    }
}