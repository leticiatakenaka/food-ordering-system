package com.example.foodorderingsystem.dto

import OrderStatus

data class OrderResponse(
    val orderId: Long,
    val status: OrderStatus,
    val customerName: String,
    val restaurant: String,
    val items: List<String>
)
