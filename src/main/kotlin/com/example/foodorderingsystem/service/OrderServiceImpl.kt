package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.entity.Order
import com.example.foodorderingsystem.enums.OrderStatus
import com.example.foodorderingsystem.enums.PaymentStatus
import com.example.foodorderingsystem.exception.BadRequestException
import com.example.foodorderingsystem.exception.NotFoundException
import com.example.foodorderingsystem.repository.OrderRepository

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.UUID

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

    override fun confirmOrder(order: Order) {
        if (order.status == OrderStatus.CONFIRMED)
            throw BadRequestException("Pedido já se encontra confirmado.")

        if (order.paymentStatus != PaymentStatus.PAID)
            throw BadRequestException("Pedido não pode ser confirmado: status de pagamento diferente de 'PAID'.")


        order.status = OrderStatus.CONFIRMED

        val saved = orderRepository.save(order)

        rabbitTemplate.convertAndSend("orders.exchange", "orders.confirmed", saved.guid?: throw IllegalArgumentException("GUID do do pedido é nulo"))
    }
}
