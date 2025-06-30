package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.entity.Order

interface OrderService  {
    fun createOrder(order: Order): Order
}