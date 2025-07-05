package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.entity.Order
import java.util.UUID

interface OrderService  {
    fun createOrder(order: Order): Order
    fun confirmOrder(order: Order)
}