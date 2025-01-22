package com.barzabaldevs.eldarwallet.data.remote

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface QRCodeApiService {
    @Headers(
        "x-rapidapi-key: eee5187e7emsh36820d4cef50353p149dcfjsn902dd8befaed",
        "x-rapidapi-host: qrcode68.p.rapidapi.com",
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("classic")
    suspend fun generateQRCode(
        @Body body: RequestBody
    ): Response<ResponseBody>
}