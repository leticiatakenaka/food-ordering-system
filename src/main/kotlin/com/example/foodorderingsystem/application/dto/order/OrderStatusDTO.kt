package com.example.foodorderingsystem.application.dto.order

import java.util.UUID

data class OrderStatusDTO(
    val orderId: UUID,
    val paymentStatus: String,
    val orderStatus: String
)
