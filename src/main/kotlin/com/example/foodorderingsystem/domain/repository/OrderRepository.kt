package com.example.foodorderingsystem.domain.repository

import com.example.foodorderingsystem.domain.model.order.OrderModel
import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderEntity
import java.util.UUID

interface OrderRepository {
    fun createOrder(orderModel: OrderModel): OrderModel
    fun confirmOrder(orderModel: OrderModel)
    fun save(orderModel: OrderModel): OrderModel
    fun findById(guid: UUID): OrderModel?
}
