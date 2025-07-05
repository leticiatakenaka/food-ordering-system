package com.example.foodorderingsystem.domain.model.order

import com.example.foodorderingsystem.application.dto.order.OrderStatusDTO
import com.example.foodorderingsystem.domain.model.customer.CustomerModel
import com.example.foodorderingsystem.domain.model.order.enums.OrderStatus
import com.example.foodorderingsystem.domain.model.payment.PaymentTypeModel
import com.example.foodorderingsystem.domain.model.payment.enums.PaymentStatus
import com.example.foodorderingsystem.domain.model.restaurant.RestaurantModel
import com.example.foodorderingsystem.interfaces.exception.BadRequestException
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import kotlin.random.Random

class OrderModel(
    val guid: UUID? = null,
    val customer: CustomerModel,
    val restaurant: RestaurantModel,
    val paymentType: PaymentTypeModel,
    val items: MutableList<OrderItemModel>,
    var status: OrderStatus? = OrderStatus.PENDING,
    var paymentStatus: PaymentStatus? = PaymentStatus.AWAITING_PAYMENT,
    val createdAt: Date? = null,
) {
    fun validateItems() {
        if (items.isEmpty()) throw BadRequestException("Pedido não pode estar vazio.")
    }

    fun validateQuantities() {
        items.forEach {
            if (it.quantity <= 0) throw BadRequestException("Quantidade inválida para item ${it.itemGuid}")
        }
    }

    fun validatePaidOrder(){
        if (paymentStatus != PaymentStatus.PAID) {
            throw BadRequestException("Pedido não se encontra pago.")
        }
    }

    fun validateConfirmedOrder() {
        if (status != OrderStatus.PENDING) {
            throw BadRequestException("Pedido já confirmado ou em outro estado.")
        }
    }

    fun confirm() {
        validateItems()
        validateQuantities()
        validatePaidOrder()
        validateConfirmedOrder()

        status = OrderStatus.CONFIRMED
    }

    fun processPayment(): OrderStatusDTO {
        Thread.sleep(3500)
        // Simulando recusa de 30% dos pagamentos
        val recusado = Random.Default.nextDouble() < 0.3
        if (recusado) {
            this.paymentStatus = PaymentStatus.REFUSED
            this.status = OrderStatus.CANCELLED
        } else {
            this.paymentStatus = PaymentStatus.PAID
        }

        return OrderStatusDTO(
            this.guid ?: throw BadRequestException("GUID do pedido é nulo"),
            this.paymentStatus.toString(),
            this.status.toString()
        )
    }
}
