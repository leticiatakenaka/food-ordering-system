package com.example.foodorderingsystem.dto

data class OrderResponse(
    val orderId: Long,
    val status: String,
    val customerName: String,
    val restaurant: String
)
