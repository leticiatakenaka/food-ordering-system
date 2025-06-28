package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.CreateOrderRequest
import com.example.foodorderingsystem.entity.Order

interface OrderService  {
    fun createOrder(request: CreateOrderRequest): Order
}