package com.example.foodorderingsystem.infrastructure.messaging

import com.example.foodorderingsystem.application.service.PaymentAppService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PaymentConsumerService(
    private val paymentAppService: PaymentAppService,
) {
     @RabbitListener(queues = ["orders.created.queue"])
     fun onOrderCreated(orderGuid: UUID) {
         println("📩 Recebido da fila: $orderGuid")
         paymentAppService.processPayment(orderGuid)
     }
}