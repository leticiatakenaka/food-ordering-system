package com.example.foodorderingsystem.repository

import com.example.foodorderingsystem.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface OrderRepository : JpaRepository<Order, UUID>
