package com.barzabaldevs.eldarwallet.ui.screens.generateQRScreen.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barzabaldevs.eldarwallet.domain.usecases.GetQRCodeUseCase
import com.barzabaldevs.eldarwallet.domain.usecases.GetUserByIDFromDataBaseUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserByIDFromDataBaseUseCase: GetUserByIDFromDataBaseUseCase,
    getQRCodeUseCase: GetQRCodeUseCase
) : ViewModel() {
    private val _qrCodeBitmap = MutableStateFlow<Bitmap?>(null)
    val qrCodeBitmap = _qrCodeBitmap.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUser = getUserByIDFromDataBaseUseCase(auth.currentUser?.uid ?: "")
            val qrBitmap = getQRCodeUseCase("${currentUser.name} ${currentUser.lastName}")
            if (qrBitmap != null) {
                _qrCodeBitmap.value = qrBitmap
                _isLoading.value = false
            }
        }
    }
}