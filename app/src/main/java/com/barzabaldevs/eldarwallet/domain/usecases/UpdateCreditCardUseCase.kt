package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import javax.inject.Inject

class UpdateCreditCardUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id : String, cards : List<CreditCard>) {
        repository.updateCreditCardUser(id, cards)
    }
}