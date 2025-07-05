package com.example.foodorderingsystem.domain.model.restaurant

import com.example.foodorderingsystem.domain.model.item.ItemModel
import java.util.Date
import java.util.UUID

class RestaurantModel(
    val guid: UUID? = null,
    val name: String = "",
    val cnpj: String = "",
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val items: List<ItemModel> = emptyList()
)
