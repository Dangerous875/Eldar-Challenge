package com.barzabaldevs.eldarwallet.domain.usecases

import com.barzabaldevs.eldarwallet.domain.Repository
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateCreditCardUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    private lateinit var updateCreditCardUseCase: UpdateCreditCardUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateCreditCardUseCase = UpdateCreditCardUseCase(repository)
    }

    private fun mockCreditCards() = listOf(
        CreditCard("1234567812345678", "123", "12/25", "Homero", "Simpson"),
        CreditCard("8765432187654321", "456", "11/26", "Homero", "Simpson")
    )

    @Test
    fun `update credit cards for a valid user`() = runBlocking {
        // Given
        val userId = "123"
        val mockCards = mockCreditCards()

        // When
        updateCreditCardUseCase(userId, mockCards)

        // Then
        coVerify(exactly = 1) { repository.updateCreditCardUser(userId, mockCards) }
    }

    @Test
    fun `does not call repository if no credit cards provided`() = runBlocking {
        // Given
        val userId = "123"
        val emptyCards = emptyList<CreditCard>()

        // When
        updateCreditCardUseCase(userId, emptyCards)

        // Then
        coVerify(exactly = 1) { repository.updateCreditCardUser(userId, emptyCards) }
    }
}