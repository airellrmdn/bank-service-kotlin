package com.practice.bankservice.service

import com.practice.bankservice.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class BankServiceTest {

    private val dataSource: BankDataSource = mockk(relaxed = true)

    private val bankService = BankService(dataSource)

    @Test
    fun `should call data source to retrieve banks`() {
        // When
        val banks = bankService.getBanks()

        // Then
        verify(exactly = 1) { dataSource.retrieveBanks() }
    }
}