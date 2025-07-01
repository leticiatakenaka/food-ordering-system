package com.example.foodorderingsystem.mapper

import com.example.foodorderingsystem.dto.*
import com.example.foodorderingsystem.entity.*
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class OrderMapper {

    fun toEntity(
        request: CreateOrderRequest,
        customer: Customer,
        restaurant: Restaurant,
        paymentType: PaymentType,
        itemResolver: (UUID) -> Item
    ): Order {
        val order = Order(
            customer = customer,
            restaurant = restaurant,
            paymentType = paymentType,
        )

        val orderItems = request.items.map {
            val itemGuid = it.itemGuid ?: throw IllegalArgumentException("Item GUID não pode ser nulo")
            val quantity = it.quantity ?: throw IllegalArgumentException("Quantidade não pode ser nula")
            val item = itemResolver(itemGuid)

            OrderItem(
                order = order,
                item = item,
                quantity = quantity,
                price = item.price.multiply(BigDecimal.valueOf(quantity.toLong()))
            )
        }

        order.items.addAll(orderItems)

        return order
    }

    fun toListDTO(orders: List<Order>): List<OrderDTO> {
        return orders.map { toDTO(it) }
    }

    fun toDTO(order: Order): OrderDTO {
        return OrderDTO(
            guid = order.guid,
            customer = CustomerDTO(order.guid, order.customer.user?.name ?: throw IllegalArgumentException()),
            restaurant = RestaurantDTO(
                order.restaurant.guid ?: throw IllegalArgumentException(),
                order.restaurant.name,
                order.restaurant.cnpj,
                order.restaurant.createdAt
                    .toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                order.restaurant.updatedAt
                    .toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ),
            paymentType = order.paymentType.guid,
            status = order.status.name,
            paymentStatus = order.paymentStatus.name,
            createdAt = order.createdAt
                ?.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            items = order.items.map { toItemDTO(it)}
        )
    }

    private fun toItemDTO(orderItem: OrderItem): OrderItemDTO {
        return OrderItemDTO(
            guid = orderItem.guid,
            name = orderItem.item.name,
            price = orderItem.item.price,
            quantity = orderItem.quantity,
            itemGuid = orderItem.item.guid,
        )
    }
}