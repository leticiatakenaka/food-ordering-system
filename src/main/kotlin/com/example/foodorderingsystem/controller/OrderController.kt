package com.example.foodorderingsystem.controller

import com.example.foodorderingsystem.dto.CreateOrderRequest
import com.example.foodorderingsystem.dto.OrderDTO
import com.example.foodorderingsystem.service.OrderAppService

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderAppService: OrderAppService,
) {
    @PostMapping
    fun createOrder(
        @RequestBody @Valid request: CreateOrderRequest
    ): ResponseEntity<OrderDTO> {
        val order = orderAppService.createOrder(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(order)
    }
}
