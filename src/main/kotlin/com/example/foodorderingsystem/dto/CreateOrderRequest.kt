package com.example.foodorderingsystem.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateOrderRequest(

    @field:NotNull(message = "O GUID do cliente é obrigatório")
    val customerGuid: UUID? = null,

    @field:NotNull(message = "O GUID do restaurante é obrigatório")
    val restaurantGuid: UUID? = null,

    @field:NotNull(message = "O tipo de pagamento é obrigatório")
    val paymentTypeGuid: UUID? = null,

    @field:NotEmpty(message = "A lista de itens não pode estar vazia")
    val items: List<CreateOrderItemRequest>
)
