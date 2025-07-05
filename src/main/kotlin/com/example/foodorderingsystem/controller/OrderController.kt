package com.example.foodorderingsystem.controller

import com.example.foodorderingsystem.dto.ConfirmOrderRequest
import com.example.foodorderingsystem.dto.CreateOrderRequest
import com.example.foodorderingsystem.dto.OrderDTO
import com.example.foodorderingsystem.dto.RestaurantDTO
import com.example.foodorderingsystem.service.OrderAppService

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

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

    @PostMapping("confirmOrder")
    fun confirmOrder(@RequestBody request: ConfirmOrderRequest): ResponseEntity<Void> {
        val uuid = UUID.fromString(request.orderId)
        orderAppService.confirmOrder(uuid)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping()
    fun getOrders(
    ): ResponseEntity<List<OrderDTO>> {
        val orders = orderAppService.getOrders()
        return ResponseEntity.status(HttpStatus.OK).body(orders)
    }
}
