package com.example.foodorderingsystem.application.dto.order

import com.example.foodorderingsystem.application.dto.customer.CustomerDTO
import com.example.foodorderingsystem.application.dto.restaurant.RestaurantDTO
import java.util.UUID

data class OrderDTO(
    val guid: UUID? = null,

    val customer: CustomerDTO,

    val restaurant: RestaurantDTO,

    val paymentType: UUID,

    val paymentStatus: String,

    val status: String,

    val createdAt: String? = null,

    val order_items: List<OrderItemDTO>
)
