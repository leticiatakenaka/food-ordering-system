package com.example.foodorderingsystem.application.service

import com.example.foodorderingsystem.application.dto.order.OrderStatusDTO
import com.example.foodorderingsystem.application.mapper.OrderMapper
import com.example.foodorderingsystem.domain.repository.OrderRepository
import com.example.foodorderingsystem.infrastructure.persistence.repository.SpringDataOrderRepository
import com.example.foodorderingsystem.interfaces.exception.NotFoundException
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentAppService(
    private val orderRepository: OrderRepository,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    fun processPayment(orderGuid: UUID) {
        val order = orderRepository.findById(orderGuid)
            ?: throw NotFoundException("Pedido não encontrado: $orderGuid")

        order.processPayment()

        val savedOrder = orderRepository.save(order)
        val dto = OrderStatusDTO(savedOrder.guid!!, savedOrder.paymentStatus.toString(), savedOrder.status.toString())

        messagingTemplate.convertAndSend("/topic/orders/${order.guid}/payment-status", dto)
    }
}
