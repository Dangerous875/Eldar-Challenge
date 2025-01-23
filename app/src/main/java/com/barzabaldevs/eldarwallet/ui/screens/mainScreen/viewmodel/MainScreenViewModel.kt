package com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.usecases.GetUserByIDFromDataBaseUseCase
import com.barzabaldevs.eldarwallet.domain.usecases.UpdateCreditCardUseCase
import com.barzabaldevs.eldarwallet.domain.usecases.UpdateUserBalanceUseCase
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
    private val updateCreditCardUseCase: UpdateCreditCardUseCase,
    private val updateUserBalanceUseCase: UpdateUserBalanceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()
    private val _simulatePay = MutableStateFlow(false)
    val simulatePay = _simulatePay.asStateFlow()
    private val _paySuccess = MutableStateFlow(false)
    val paySuccess = _paySuccess.asStateFlow()

    init {
        initUserData()
    }

    fun initUserData() {
        viewModelScope.launch {
            val currentUser = getUserByIDFromDataBaseUseCase(auth.currentUser?.uid ?: "")
            _uiState.update {
                delay(2000L)
                it.copy(
                    isLoading = false,
                    userData = currentUser,
                    creditCards = currentUser.creditCards
                )
            }
        }
    }

    fun addCreditCard(
        cardNumber: String,
        securityCode: String,
        expirationDate: String,
        name: String,
        lastName: String
    ) = viewModelScope.launch {
        val creditCard = CreditCard(cardNumber, securityCode, expirationDate, name, lastName)
        _uiState.update { it.copy(creditCards = it.creditCards.plus(creditCard)) }
        withContext(Dispatchers.IO) {
            updateCreditCardUseCase(auth.uid!!, _uiState.value.creditCards)
        }
    }

    fun setSelectedCard(cardSelected: CreditCard) {
        _uiState.update { it.copy(cardSelected = cardSelected) }
    }

    fun processPayment(amountToPay: String) {
        val currentBalance = _uiState.value.userData?.balance ?: 0.0
        val newBalance = currentBalance - amountToPay.toDouble()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateUserBalanceUseCase(auth.uid!!, newBalance)
            }
            _simulatePay.value = true
            delay(3000L)
            _simulatePay.value = false
            _paySuccess.value = true
        }
    }

    fun resetPay() {
        _paySuccess.value = false
    }

}


