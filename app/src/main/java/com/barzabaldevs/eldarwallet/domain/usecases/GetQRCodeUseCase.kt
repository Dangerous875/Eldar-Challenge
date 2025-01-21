package com.barzabaldevs.eldarwallet.domain.usecases

import android.graphics.Bitmap
import com.barzabaldevs.eldarwallet.domain.Repository
import javax.inject.Inject

class GetQRCodeUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Bitmap? = repository.getQRCode()
}