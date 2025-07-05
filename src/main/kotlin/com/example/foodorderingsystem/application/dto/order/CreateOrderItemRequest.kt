package com.example.foodorderingsystem.application.dto.order

import jakarta.validation.constraints.NotNull
import java.util.UUID


data class CreateOrderItemRequest(

    @field:NotNull(message = "O GUID do item é obrigatório")
    val itemGuid: UUID? = null,

    @field:NotNull(message = "A quantidade é obrigatória")
    val quantity: Int? = null
)