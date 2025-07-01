package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.OrderStatusDTO
import com.example.foodorderingsystem.enums.PaymentStatus
import com.example.foodorderingsystem.repository.OrderRepository
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import kotlin.random.Random
import java.util.UUID

@Component
class OrderConsumerService(
    private val orderRepository: OrderRepository,
    private val messagingTemplate: SimpMessagingTemplate,
) {
     @RabbitListener(queues = ["orders.queue"])
     fun processPayment(orderGuid: UUID) {
         println("📩 Recebido da fila: $orderGuid")

         val orderOptional = orderRepository.findById(orderGuid)

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

             val orderSaved = orderRepository.save(pedido)

             val dto = OrderStatusDTO(orderSaved.guid?: throw IllegalArgumentException("GUID do pedidio é nulo"), orderSaved.paymentStatus.toString())

             messagingTemplate.convertAndSend("/topic/orders/${pedido.guid}/payment-status", dto)
         } else {
             println("⚠️ Pedido com ID ${orderGuid} não encontrado.")
         }
     }
}
