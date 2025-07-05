package com.example.foodorderingsystem.domain.service

import com.example.foodorderingsystem.domain.model.order.OrderModel
import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderEntity

interface OrderService  {
    fun createOrder(orderModel: OrderModel): OrderModel
    fun confirmOrder(order: OrderEntity)
}