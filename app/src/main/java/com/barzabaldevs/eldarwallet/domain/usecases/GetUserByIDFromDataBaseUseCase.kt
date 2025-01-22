package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import javax.inject.Inject

class GetUserByIDFromDataBaseUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: String) = repository.getUserById(id)
}