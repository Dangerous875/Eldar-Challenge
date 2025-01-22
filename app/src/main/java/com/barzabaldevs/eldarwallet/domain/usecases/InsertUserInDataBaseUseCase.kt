package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import com.barzabaldevs.eldarwallet.domain.model.UserModel
import javax.inject.Inject

class InsertUserInDataBaseUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: String, email: String, name: String, lastName: String) {
        val newUser= UserModel(id, email, name, lastName)
        repository.insertUser(newUser)
    }
}