package com.example.foodorderingsystem.application.dto.restaurant

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID

data class RestaurantDTO (
    val guid: UUID,
    val name: String,
    val cnpj: String,
    @JsonIgnore
    val createdAt: String? = null,
    @JsonIgnore
    val updatedAt: String? = null
)