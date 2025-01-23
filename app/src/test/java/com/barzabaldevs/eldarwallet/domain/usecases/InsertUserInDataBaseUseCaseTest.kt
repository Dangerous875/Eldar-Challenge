package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import com.barzabaldevs.eldarwallet.domain.model.UserModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertUserInDataBaseUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    private lateinit var insertUserInDataBaseUseCase: InsertUserInDataBaseUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        insertUserInDataBaseUseCase = InsertUserInDataBaseUseCase(repository)
    }

    @Test
    fun `insert user with valid data`() = runBlocking {
        // Arrange
        val userId = "123"
        val email = "homero@gmail.com"
        val name = "Homero"
        val lastName = "Simpson"
        val expectedUser = UserModel(
            id = userId,
            email = email,
            name = name,
            lastName = lastName
        )

        // Act
        insertUserInDataBaseUseCase(userId, email, name, lastName)

        // Assert
        coVerify(exactly = 1) { repository.insertUser(expectedUser) }
    }
}