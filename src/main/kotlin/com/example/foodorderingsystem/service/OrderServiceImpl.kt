package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.entity.Order
import com.example.foodorderingsystem.mapper.OrderMapper
import com.example.foodorderingsystem.repository.OrderRepository

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val rabbitTemplate: RabbitTemplate,
    private val orderMapper: OrderMapper
) : OrderService {
    override fun createOrder(order: Order): Order {
        val saved = orderRepository.save(order)

        val dto = orderMapper.toDTO(saved)

        rabbitTemplate.convertAndSend("orders.exchange", "orders.created", dto.guid!!)

        return saved
    }
}
