package com.example.foodorderingsystem.controller

import com.example.foodorderingsystem.dto.CreateOrderRequest
import com.example.foodorderingsystem.dto.OrderResponse
import com.example.foodorderingsystem.service.OrderService

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/createOrder")
    fun createOrder(
        @RequestBody @Valid request: CreateOrderRequest
    ): ResponseEntity<OrderResponse> {
        val order = orderService.createOrder(request)
        val response = OrderResponse(
            orderId = order.id,
            status = order.status.name,
            customerName = order.customerName,
            restaurant = order.restaurant
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
