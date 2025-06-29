package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.OrderDTO
import com.example.foodorderingsystem.entity.Order
import com.example.foodorderingsystem.entity.PaymentType
import com.example.foodorderingsystem.enums.OrderStatus
import com.example.foodorderingsystem.enums.PaymentStatus
import com.example.foodorderingsystem.repository.OrderRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import kotlin.random.Random

@Component
class OrderConsumerService(
    private val orderRepository: OrderRepository,
    private val messagingTemplate: SimpMessagingTemplate
) {
    private val objectMapper = jacksonObjectMapper()

    @RabbitListener(queues = ["orders.queue"])
    fun receive(orderMessage: Order) {
        println("📩 Recebido da fila: $orderMessage")

        val orderOptional = orderRepository.findById(orderMessage.guid ?: throw IllegalArgumentException())

        if (orderOptional.isPresent) {
            val pedido = orderOptional.get()

            Thread.sleep(3500)

            // Simulando recusa de 30% dos pagamentos
            val recusado = Random.nextDouble() < 0.3
            if (recusado) {
                pedido.paymentStatus = PaymentStatus.REFUSED
                println("❌ Pagamento recusado para o pedido ${pedido.guid}.")
            } else {
                pedido.paymentStatus = PaymentStatus.PAID
                println("✅ Pagamento aprovado para o pedido ${pedido.guid}.")
            }

            orderRepository.save(pedido)

            val statusUpdate = mapOf(
                "id" to pedido.guid,
                "status" to pedido.status,
                "paymentStatus" to pedido.paymentStatus,
                "updatedAt" to LocalDateTime.now()
            )

            messagingTemplate.convertAndSend("/topic/orders/${pedido.guid}/status", statusUpdate)
        } else {
            println("⚠️ Pedido com ID ${orderMessage.guid} não encontrado.")
        }
    }
}
