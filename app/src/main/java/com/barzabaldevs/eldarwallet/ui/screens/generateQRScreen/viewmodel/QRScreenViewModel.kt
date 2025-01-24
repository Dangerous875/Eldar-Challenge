package com.barzabaldevs.eldarwallet.ui.screens.generateQRScreen.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barzabaldevs.eldarwallet.di.NetworkUtils
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
    private val getQRCodeUseCase: GetQRCodeUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _qrCodeBitmap = MutableStateFlow<Bitmap?>(null)
    val qrCodeBitmap = _qrCodeBitmap.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isInternetAvailable = MutableStateFlow(false)
    val isInternetAvailable = _isInternetAvailable.asStateFlow()

    init {
        checkInternetAndLoadQR()
    }

    private fun checkInternetAndLoadQR() {
        _isInternetAvailable.value = networkUtils.isInternetAvailable()
        if (_isInternetAvailable.value) {
            loadQRCode()
        }
    }

    private fun loadQRCode() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentUser = auth.currentUser?.uid?.let { getUserByIDFromDataBaseUseCase(it) }
                if (currentUser != null) {
                    val qrBitmap = getQRCodeUseCase("${currentUser.name} ${currentUser.lastName}")
                    _qrCodeBitmap.value = qrBitmap
                }
            } catch (e: Exception) {
                Log.i("ApiFailed", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retryLoadQR() {
        checkInternetAndLoadQR()
    }
}