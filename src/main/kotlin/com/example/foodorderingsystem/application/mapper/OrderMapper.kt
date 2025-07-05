package com.example.foodorderingsystem.application.mapper

import com.example.foodorderingsystem.application.dto.customer.CustomerDTO
import com.example.foodorderingsystem.application.dto.order.CreateOrderRequest
import com.example.foodorderingsystem.application.dto.order.OrderDTO
import com.example.foodorderingsystem.application.dto.order.OrderItemDTO
import com.example.foodorderingsystem.application.dto.restaurant.RestaurantDTO
import com.example.foodorderingsystem.domain.model.foodtype.FoodTypeModel
import com.example.foodorderingsystem.domain.model.customer.CustomerModel
import com.example.foodorderingsystem.domain.model.order.OrderItemModel
import com.example.foodorderingsystem.domain.model.order.OrderModel
import com.example.foodorderingsystem.domain.model.payment.PaymentTypeModel
import com.example.foodorderingsystem.domain.model.restaurant.RestaurantModel
import com.example.foodorderingsystem.domain.model.restaurant.enums.FoodType
import com.example.foodorderingsystem.infrastructure.persistence.entity.CustomerEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.FoodTypeEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.ItemEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderItemEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.PaymentTypeEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.RestaurantEntity
import com.example.foodorderingsystem.interfaces.exception.BadRequestException
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class OrderMapper {
    fun dtoToModel(createOrderRequest: CreateOrderRequest): OrderModel {
        return OrderModel(
            guid = null,
            customer = CustomerModel(createOrderRequest.customerGuid!!),
            restaurant = RestaurantModel(
                guid = createOrderRequest.restaurantGuid!!,
                name = "",
                cnpj = "",
                createdAt = null,
                updatedAt = null
            ),
            paymentType = PaymentTypeModel(
                guid = createOrderRequest.paymentTypeGuid!!,
                description = null
            ),
            items = createOrderRequest.items.map {
                OrderItemModel(null, it.itemGuid!!, null, null,0)
            }.toMutableList()
        )
    }

    fun modelToEntity(orderModel: OrderModel): OrderEntity {
        val orderEntity = OrderEntity(
            guid = orderModel.guid,
            paymentStatus = orderModel.paymentStatus!!,
            status = orderModel.status!!,
            customer = null,
            restaurant = null,
            paymentType = null,
            items = mutableListOf()
        )

        return orderEntity
    }

    fun entityToModel(orderEntity: OrderEntity): OrderModel {
        return OrderModel(
            guid = orderEntity.guid,
            customer = CustomerModel(
                guid = orderEntity.customer?.guid ?: throw BadRequestException("Cliente não pode ser nulo")
            ),
            createdAt = orderEntity.createdAt!!,
            restaurant = RestaurantModel(
                guid = orderEntity.restaurant?.guid ?: throw BadRequestException("Restaurante não pode ser nulo"),
                name = orderEntity.restaurant?.name ?: "",
                cnpj = orderEntity.restaurant?.cnpj ?: "",
                createdAt = orderEntity.restaurant?.createdAt
            ),
            paymentType = PaymentTypeModel(
                guid = orderEntity.paymentType?.guid ?: throw BadRequestException("Tipo de pagamento não pode ser nulo"),
                description = orderEntity.paymentType?.description
            ),
            paymentStatus = orderEntity.paymentStatus,
            status = orderEntity.status,
            items = orderEntity.items.map { orderItemEntity ->
                OrderItemModel(
                    itemGuid = orderItemEntity.item?.guid ?: throw BadRequestException("Item não pode ser nulo"),
                    name = orderItemEntity.item?.name,
                    price = orderItemEntity.price,
                    quantity = orderItemEntity.quantity,
                    foodType = orderItemEntity.item?.foodType?.let { foodTypeEntity ->
                        FoodTypeModel(
                            guid = foodTypeEntity.guid,
                            description = foodTypeEntity.description
                        )
                    }
                )
            } as MutableList<OrderItemModel>
        )
    }

    fun modelToDTO(orderModel: OrderModel): OrderDTO {
        return OrderDTO(
            orderModel.guid ?: UUID.randomUUID(),
            CustomerDTO(
                orderModel.customer.guid,
            ),
            RestaurantDTO(
                orderModel.restaurant.guid!!,
                orderModel.restaurant.name,
                orderModel.restaurant.cnpj
            ),
            orderModel.paymentType.guid,
            orderModel.paymentStatus.toString(),
            orderModel.status?.name!!,
            orderModel.createdAt
                ?.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            orderModel.items.map {
                OrderItemDTO(
                    null,
                    it.itemGuid,
                    it.quantity,
                    it.name!!,
                    it.price!!.multiply(BigDecimal(it.quantity)),
                )
            }
        )
    }

    fun toListDTO(orders: List<OrderEntity>): List<OrderDTO> {
        return orders.map { toDTO(it) }
    }

    fun toDTO(order: OrderEntity): OrderDTO {
        return OrderDTO(
            guid = order.guid,
            customer = CustomerDTO(order.guid),
            restaurant = RestaurantDTO(
                order.restaurant?.guid ?: throw BadRequestException("O GUID do restaurante não pode ser nulo."),
                order.restaurant?.name!!,
                order.restaurant?.cnpj!!,
                order.restaurant?.createdAt
                    ?.toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                order.restaurant?.updatedAt
                    ?.toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ),
            paymentType = order.paymentType?.guid!!,
            status = order.status.name,
            paymentStatus = order.paymentStatus.name,
            createdAt = order.createdAt
                ?.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            order_items = order.items.map { toItemDTO(it) }
        )
    }

    private fun toItemDTO(orderItem: OrderItemEntity): OrderItemDTO {
        return OrderItemDTO(
            guid = orderItem.guid,
            name = orderItem.item?.name!!,
            price = orderItem.item?.price!!,
            quantity = orderItem.quantity,
            itemGuid = orderItem.item?.guid!!,
        )
    }
}