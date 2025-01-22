package com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barzabaldevs.eldarwallet.di.EncryptionUtils
import com.barzabaldevs.eldarwallet.di.JsonUtils
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.usecases.GetUserByIDFromDataBaseUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserByIDFromDataBaseUseCase: GetUserByIDFromDataBaseUseCase,
    private val encryptionUtils: EncryptionUtils,
    private val jsonUtils: JsonUtils
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        val card = listOf(CreditCard("1234567812345678", "123", "12/25", "John", "Doe"),CreditCard("1234567812345678", "123", "12/25", "John", "Doe"))
        //lista original
        Log.i("CryptoTest1",card.toString())

        // La paso a json
        val jsonList =jsonUtils.encodeToJson(card)
        Log.i("CryptoTest2",jsonList)

        // La encripto
        val crypto = encryptionUtils.encrypt(jsonList)
        Log.i("CryptoTest3", crypto)

        // La desencripto
        val decode = encryptionUtils.decrypt(crypto)
        Log.i("CryptoTest4", decode)

        // La paso a objeto
        val type = object : TypeToken<List<CreditCard>>() {}.type
        val original2 = jsonUtils.decodeFromJson<List<CreditCard>>(decode, type)
        Log.i("CryptoTest5", original2.toString())


        viewModelScope.launch {
            val currentUser = getUserByIDFromDataBaseUseCase(auth.currentUser?.uid ?: "")
            _uiState.update { it.copy(isLoading = false, userData = currentUser) }
        }
    }
}