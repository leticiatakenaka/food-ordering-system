package com.example.foodorderingsystem.dto

import java.util.UUID

data class OrderDTO(
    val guid: UUID? = null,

    val customer: CustomerDTO,

    val restaurant: RestaurantDTO,

    val paymentType: UUID,

    val paymentStatus: String,

    val status: String,

    val createdAt: String? = null,

    val items: List<OrderItemDTO>
)
