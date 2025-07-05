package com.example.foodorderingsystem.domain.model.order

import com.example.foodorderingsystem.domain.model.foodtype.FoodTypeModel
import java.math.BigDecimal
import java.util.UUID

data class OrderItemModel(
    val guid: UUID? = null,
    val itemGuid: UUID,
    val price: BigDecimal? = null,
    val name: String? = null,
    val quantity: Int = 0,
    val foodType: FoodTypeModel? = null
) {
}
