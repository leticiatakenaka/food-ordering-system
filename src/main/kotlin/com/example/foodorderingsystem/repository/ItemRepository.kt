package com.example.foodorderingsystem.repository

import com.example.foodorderingsystem.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ItemRepository : JpaRepository<Item, UUID> {
    fun findByRestaurantGuid(restaurantGuid: UUID): List<Item>
}