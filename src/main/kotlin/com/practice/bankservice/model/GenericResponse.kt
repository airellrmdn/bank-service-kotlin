package com.practice.bankservice.model

data class GenericResponse(
    val success: Boolean,
    val errors: String,
    val errorMessage: String?,
    val data: Any?
) {
    companion object {
        fun createErrorResponse(errors: String, errorMessage: String? = null): GenericResponse {
            return GenericResponse(false, errors, errorMessage, null)
        }
    }
}