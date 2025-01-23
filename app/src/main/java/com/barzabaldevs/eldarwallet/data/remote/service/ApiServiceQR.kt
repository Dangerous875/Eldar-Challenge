package com.barzabaldevs.eldarwallet.data.remote.service

import com.barzabaldevs.eldarwallet.data.remote.QRCodeApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ApiServiceQR @Inject constructor(private val apiService: QRCodeApiService) {

    suspend fun generateQRCode(fullName: String): Response<ResponseBody> {
        val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
        val body = "text=$fullName".toRequestBody(mediaType)
        return apiService.generateQRCode(body)
    }
}