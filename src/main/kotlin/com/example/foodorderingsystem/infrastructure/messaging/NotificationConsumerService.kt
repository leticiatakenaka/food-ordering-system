package com.example.foodorderingsystem.infrastructure.messaging

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NotificationConsumerService(
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @RabbitListener(queues = ["orders.confirmed.queue"])
    fun notifyOrderConfirmed(orderGuid: UUID) {
        messagingTemplate.convertAndSend("/topic/orders/${orderGuid}/confirmed-orders", "CONFIRMED")
    }
}