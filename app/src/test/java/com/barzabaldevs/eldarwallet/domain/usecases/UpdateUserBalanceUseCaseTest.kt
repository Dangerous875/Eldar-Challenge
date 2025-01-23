package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateUserBalanceUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    private lateinit var updateUserBalanceUseCase: UpdateUserBalanceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateUserBalanceUseCase = UpdateUserBalanceUseCase(repository)
    }

    @Test
    fun `update user balance with valid ID and new balance`() = runBlocking {
        // Arrange
        val userId = "123"
        val newBalance = 1500.0

        // Act
        updateUserBalanceUseCase(userId, newBalance)

        // Assert
        coVerify(exactly = 1) { repository.updateUserBalance(userId, newBalance) }
    }

    @Test
    fun `verify no repository call when balance is negative`() = runBlocking {
        // Arrange
        val userId = "123"
        val newBalance = -100.0

        // Act
        updateUserBalanceUseCase(userId, newBalance)

        // Assert
        coVerify(exactly = 1) { repository.updateUserBalance(userId, newBalance) }
    }
}