package com.example.foodorderingsystem.dto

import java.math.BigDecimal
import java.util.UUID

data class ItemDTO(
    val guid: UUID,
    val name: String,
    val price: BigDecimal
)