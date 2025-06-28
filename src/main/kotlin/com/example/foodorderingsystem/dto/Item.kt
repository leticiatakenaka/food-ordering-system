package com.example.foodorderingsystem.dto

import jakarta.validation.constraints.NotBlank

data class Item(
    @field:NotBlank val itemName: String,
    @field:NotBlank val quantity: Int
)