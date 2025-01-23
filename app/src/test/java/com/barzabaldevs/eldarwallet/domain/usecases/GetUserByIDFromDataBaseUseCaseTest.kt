package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.model.UserModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserByIDFromDataBaseUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    private lateinit var getUserByIDFromDataBaseUseCase: GetUserByIDFromDataBaseUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserByIDFromDataBaseUseCase = GetUserByIDFromDataBaseUseCase(repository)
    }

    private fun mockUser() = UserModel(
        id = "123",
        email = "homero@gmail.com",
        name = "homero",
        lastName = "Simpson",
        balance = 1000.0,
        creditCards = listOf(
            CreditCard("1234567812345678", "123", "12/25", "homero", "Simpson"),
            CreditCard("1234567812345678", "123", "12/25", "homero", "Simpson")
        )
    )

    @Test
    fun `return user when ID is valid`() = runBlocking {
        // Arrange
        val mockUser = UserModel(
            id = "123",
            email = "homero@gmail.com",
            name = "homero",
            lastName = "Simpson",
            balance = 1000.0,
            creditCards = listOf(
                CreditCard("1234 5678 9012 3456", "123", "12/25", "homero", "Simpson"),
                CreditCard("2345 6789 0123 4567", "456", "11/26", "homero", "Simpson")
            )
        )
        val userId = "123"
        coEvery { repository.getUserById(userId) } returns mockUser

        // Act
        val result = getUserByIDFromDataBaseUseCase(userId)

        // Assert
        assertEquals("123", result.id)
        assertEquals("homero@gmail.com", result.email)
        assertEquals("homero", result.name)
        assertEquals("Simpson", result.lastName)
        assertEquals(1000.0, result.balance, 0.0)
        assertEquals(2, result.creditCards.size)
        assertEquals("1234 5678 9012 3456", result.creditCards[0].cardNumber)

        coVerify(exactly = 1) { repository.getUserById(userId) }
    }

}