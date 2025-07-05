package com.example.foodorderingsystem.middleware

import com.example.foodorderingsystem.exception.BadRequestException
import com.example.foodorderingsystem.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message.orEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message.orEmpty())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = ex.bindingResult?.fieldErrors?.associate { it.field to (it.defaultMessage ?: "Invalid") }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to "Erro inesperado: ${ex.message}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseError(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, String>> {
        val message = if (ex.message?.contains("UUID") == true) {
            "Um dos campos UUID está mal formatado. Use o padrão com 36 caracteres."
        } else {
            "Erro ao ler o corpo da requisição: ${ex.localizedMessage}"
        }
        val body = mapOf("error" to message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleInvalidUUID(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to "Formato de UUID inválido")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }
}
