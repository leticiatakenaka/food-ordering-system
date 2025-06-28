package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.OrderResponse
import com.example.foodorderingsystem.repository.OrderRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderConsumer(
    private val orderRepository: OrderRepository,
    private val messagingTemplate: SimpMessagingTemplate
) {
    private val objectMapper = jacksonObjectMapper()

    @RabbitListener(queues = ["orders.queue"])
    fun receive(orderMessage: OrderResponse) {
        //Thread.sleep(5500);

        println("📩 Recebido da fila: $orderMessage")

        val orderOptional = orderRepository.findById(orderMessage.orderId)

        if (orderOptional.isPresent) {
            val pedido = orderOptional.get()
            pedido.status = OrderStatus.PROCESSING
            orderRepository.save(pedido)
            println("✅ Pedido ${pedido.id} em preparação.")

            val statusUpdate = mapOf(
                "id" to pedido.id,
                "status" to pedido.status,
                "updatedAt" to LocalDateTime.now()
            )

            messagingTemplate.convertAndSend("/topic/orders/${pedido.id}/status", statusUpdate)
        } else {
            println("⚠️ Pedido com ID ${orderMessage.orderId} não encontrado.")
        }
    }
}
