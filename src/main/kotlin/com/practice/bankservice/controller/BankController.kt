package com.practice.bankservice.controller

import com.practice.bankservice.model.Bank
import com.practice.bankservice.model.GenericResponse
import com.practice.bankservice.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<GenericResponse> {
        val response = GenericResponse.createErrorResponse("NoSuchElementException", e.message)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank = service.getBank(accountNumber)
}