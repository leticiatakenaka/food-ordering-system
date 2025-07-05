package com.example.foodorderingsystem.domain.repository

import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderItemRepository : JpaRepository<OrderItemEntity, UUID>{
}