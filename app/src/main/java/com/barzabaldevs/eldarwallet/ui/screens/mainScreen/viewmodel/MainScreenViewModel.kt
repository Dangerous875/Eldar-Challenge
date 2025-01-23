package com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.usecases.UpdateCreditCardUseCase
import com.barzabaldevs.eldarwallet.domain.usecases.GetUserByIDFromDataBaseUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserByIDFromDataBaseUseCase: GetUserByIDFromDataBaseUseCase,
    private val updateCreditCardUseCase: UpdateCreditCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        initUserData()
    }

    private fun initUserData() {
        viewModelScope.launch {
            val currentUser = getUserByIDFromDataBaseUseCase(auth.currentUser?.uid ?: "")
            _uiState.update {
                delay(3000L)
                it.copy(
                    isLoading = false,
                    userData = currentUser,
                    creditCards = currentUser.creditCards
                )
            }
        }
    }

    fun addCreditCard() = viewModelScope.launch {
        val creditCard = CreditCard("971180303257522", "123", "12/25", "John", "Doe")
        _uiState.update { it.copy(creditCards = it.creditCards.plus(creditCard)) }
        withContext(Dispatchers.IO){
            updateCreditCardUseCase(auth.uid!!, _uiState.value.creditCards)
        }
    }


}