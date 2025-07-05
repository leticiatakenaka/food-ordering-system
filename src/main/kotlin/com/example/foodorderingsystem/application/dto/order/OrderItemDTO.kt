package com.example.foodorderingsystem.application.dto.order

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.UUID

data class OrderItemDTO(
    @JsonIgnore
    val guid: UUID? = null,
    @JsonIgnore
    @field:NotBlank val itemGuid: UUID,
    @field:NotBlank val quantity: Int,
    val name: String,
    val price: BigDecimal,
)