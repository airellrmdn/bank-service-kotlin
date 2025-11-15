package com.practice.bankservice.datasource.mock

import com.practice.bankservice.datasource.BankDataSource
import com.practice.bankservice.model.Bank
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    private val logger = LoggerFactory.getLogger(MockBankDataSource::class.java)

    val banks = mutableListOf(
        Bank("1200", 3.1, 10),
        Bank("1201", 0.17, 0),
        Bank("1203", 0.0, 100),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            logger.error("bank account number is already exist")
            throw IllegalArgumentException("Bank account is already exist")
        }

        banks.add(bank)

        logger.info("add new bank account success")
        return bank
    }
}