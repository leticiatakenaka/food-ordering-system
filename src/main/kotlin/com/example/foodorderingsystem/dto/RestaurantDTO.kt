package com.example.foodorderingsystem.dto

import java.util.UUID

data class RestaurantDTO (
    val guid: UUID,
    val name: String,
    val cnpj: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)