package com.example.foodorderingsystem.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class CreateOrderRequest(
    @field:NotBlank val customerName: String,
    @field:NotBlank val restaurant: String,
    @field:NotEmpty val items: List<Item>
)
