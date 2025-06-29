package com.example.foodorderingsystem.repository

import com.example.foodorderingsystem.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderItemRepository : JpaRepository<OrderItem, UUID>{
}