package com.example.foodorderingsystem.dto

import java.util.UUID

data class OrderStatusDTO(
    val orderId: UUID,
    val paymentStatus: String,
    val orderStatus: String
)
