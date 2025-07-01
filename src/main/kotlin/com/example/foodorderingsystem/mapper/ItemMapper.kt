package com.example.foodorderingsystem.mapper

import com.example.foodorderingsystem.dto.ItemDTO
import com.example.foodorderingsystem.entity.Item
import org.springframework.stereotype.Component

@Component
class ItemMapper {
    fun toListDTO(items: List<Item>): List<ItemDTO> {
        return items.map { item ->
            ItemDTO(
                guid = item.guid,
                name = item.name,
                price = item.price
            )
        }
    }
}