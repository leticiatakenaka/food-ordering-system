package com.example.foodorderingsystem.infrastructure.messaging

import com.example.foodorderingsystem.application.dto.order.OrderStatusDTO
import com.example.foodorderingsystem.application.mapper.OrderMapper
import com.example.foodorderingsystem.application.service.PaymentAppService
import com.example.foodorderingsystem.domain.model.order.enums.OrderStatus
import com.example.foodorderingsystem.domain.model.payment.enums.PaymentStatus
import com.example.foodorderingsystem.domain.repository.OrderRepository
import com.example.foodorderingsystem.infrastructure.persistence.repository.SpringDataOrderRepository
import com.example.foodorderingsystem.interfaces.exception.NotFoundException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class OrderConsumerService(
    private val paymentAppService: PaymentAppService,
    private val messagingTemplate: SimpMessagingTemplate,
) {
     @RabbitListener(queues = ["orders.created.queue"])
     fun onOrderCreated(orderGuid: UUID) {
         println("📩 Recebido da fila: $orderGuid")
         paymentAppService.processPayment(orderGuid)
     }

     @RabbitListener(queues = ["orders.confirmed.queue"])
     fun notifyOrderConfirmed(orderGuid: UUID) {
         messagingTemplate.convertAndSend("/topic/orders/${orderGuid}/confirmed-orders", "CONFIRMED")
     }
}