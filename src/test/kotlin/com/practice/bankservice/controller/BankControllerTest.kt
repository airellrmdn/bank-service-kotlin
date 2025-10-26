package com.practice.bankservice.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val baseUri = "/api/banks"

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks`() {
            // When and Then
            mockMvc.get(baseUri)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1200") }
                }
        }
    }

    @Nested
    @DisplayName("getBank()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank details with the given account number`() {
            // Given
            val accountNumber = 1200

            // When and Then
            mockMvc.get("$baseUri/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { MediaType.APPLICATION_JSON }
                    jsonPath("$.trust") { value("3.1") }
                    jsonPath("$.transactionFee") { value("10") }
                }
        }

        @Test
        fun `should return Not Found if the account number does not exist`() {
            // Given
            val accountNumber = "12121"

            // When and Then
            mockMvc.get("$baseUri/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
}