package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.CreateOrderRequest
import com.example.foodorderingsystem.entity.Order
import com.example.foodorderingsystem.entity.OrderItem
import com.example.foodorderingsystem.repository.OrderRepository

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val rabbitTemplate: RabbitTemplate
) : OrderService {

    override fun createOrder(request: CreateOrderRequest): Order {
        val order = Order(
            customerName = request.customerName,
            restaurant = request.restaurant,
            items = mutableListOf(),
            status = OrderStatus.PENDING
        )

        val orderItems = request.items.map {
            OrderItem(
                itemName = it.itemName,
                quantity = it.quantity,
                order = order
            )
        }

        order.items.addAll(orderItems)

        val saved = orderRepository.save(order)

        val payload = mapOf(
            "orderId" to saved.id,
            "customerName" to saved.customerName,
            "restaurant" to saved.restaurant,
            "items" to saved.items.map { it.itemName },
            "status" to saved.status
        )

        rabbitTemplate.convertAndSend("orders.exchange", "orders.created", payload)

        return saved
    }
}
