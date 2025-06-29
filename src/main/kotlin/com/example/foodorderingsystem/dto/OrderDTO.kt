package com.example.foodorderingsystem.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class OrderDTO(
    val guid: UUID? = null,

    val customerGuid: UUID? = null,

    val restaurantGuid: UUID? = null,

    val paymentType: UUID,

    val customerName: String,

    val restaurantName: String,

    val status: String,

    val paymentStatus: String,

    val createdAt: String? = null,

    val items: List<OrderItemDTO>
)
