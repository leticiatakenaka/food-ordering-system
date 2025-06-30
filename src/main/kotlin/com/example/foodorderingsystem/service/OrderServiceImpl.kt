package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.entity.Order
import com.example.foodorderingsystem.repository.OrderRepository

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val rabbitTemplate: RabbitTemplate,
) : OrderService {
    override fun createOrder(order: Order): Order {
        val saved = orderRepository.save(order)

        rabbitTemplate.convertAndSend("orders.exchange", "orders.created", saved.guid?: throw IllegalArgumentException("GUID do do pedido é nulo"))

        return saved
    }
}
