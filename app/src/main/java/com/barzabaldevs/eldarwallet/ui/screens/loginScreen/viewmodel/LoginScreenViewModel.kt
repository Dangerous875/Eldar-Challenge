package com.barzabaldevs.eldarwallet.ui.screens.loginScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barzabaldevs.eldarwallet.domain.usecases.InsertUserInDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val insertUserInDataBaseUseCase: InsertUserInDataBaseUseCase) : ViewModel() {

    fun createUserInDatabase(id: String, email: String, name: String, lastName: String) {
        viewModelScope.launch {
            insertUserInDataBaseUseCase(id, email, name, lastName)
        }
    }

}