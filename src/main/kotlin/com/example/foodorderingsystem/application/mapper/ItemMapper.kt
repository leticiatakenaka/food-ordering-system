package com.example.foodorderingsystem.application.mapper

import com.example.foodorderingsystem.application.dto.order.ItemDTO
import com.example.foodorderingsystem.infrastructure.persistence.entity.ItemEntity
import org.springframework.stereotype.Component

@Component
class ItemMapper {
    fun toListDTO(items: List<ItemEntity>): List<ItemDTO> {
        return items.map { item ->
            ItemDTO(
                guid = item.guid,
                name = item.name,
                price = item.price
            )
        }
    }
}