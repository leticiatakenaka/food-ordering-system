package com.example.foodorderingsystem.dto

import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.UUID

data class OrderItemDTO(
    val guid: UUID? = null,
    @field:NotBlank val itemGuid: UUID,
    @field:NotBlank val quantity: Int,
    val name: String,
    val price: BigDecimal,
)