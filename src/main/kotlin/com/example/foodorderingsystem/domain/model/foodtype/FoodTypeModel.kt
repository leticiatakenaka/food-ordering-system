package com.example.foodorderingsystem.domain.model.foodtype

import com.example.foodorderingsystem.domain.model.restaurant.enums.FoodType
import java.util.UUID

class FoodTypeModel (
    val guid: UUID? = null,
    val description: FoodType
)