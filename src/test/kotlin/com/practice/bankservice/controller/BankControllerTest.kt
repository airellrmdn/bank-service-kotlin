package com.practice.bankservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.bankservice.model.Bank
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
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val baseUri = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
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
    @DisplayName("GET /api/banks/{accountNumber}")
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

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add a new bank`() {
            // Given
            val newBank = Bank("123", 12.1, 5)

            // When
            val post = mockMvc.post(baseUri) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // Then
            post.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("123") }
                    jsonPath("$.trust") { value("12.1") }
                    jsonPath("$.transactionFee") { value("5") }
                }
        }

        @Test
        fun `should return NOT FOUND if bank account already exist`() {
            // Given
            val invalidBank = Bank("1203", 12.1, 5)

            // When
            val post = mockMvc.post(baseUri) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // Then
            post.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBanks {

        @Test
        fun `should update an existing bank account`() {
            // When and Then
            mockMvc.patch(baseUri)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1200") }
                }
        }
    }
}