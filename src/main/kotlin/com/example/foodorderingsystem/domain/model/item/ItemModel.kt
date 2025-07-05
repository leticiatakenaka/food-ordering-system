package com.example.foodorderingsystem.domain.model.item

import com.example.foodorderingsystem.domain.model.restaurant.RestaurantModel
import com.example.foodorderingsystem.domain.model.restaurant.enums.FoodType
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

class ItemModel (
    val guid: UUID,
    val name: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val restaurant: RestaurantModel,
    val foodType: FoodType
)