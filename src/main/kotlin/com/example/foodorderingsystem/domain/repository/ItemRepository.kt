package com.example.foodorderingsystem.domain.repository

import com.example.foodorderingsystem.infrastructure.persistence.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ItemRepository : JpaRepository<ItemEntity, UUID> {
    fun findByRestaurantGuid(restaurantGuid: UUID): List<ItemEntity>
}