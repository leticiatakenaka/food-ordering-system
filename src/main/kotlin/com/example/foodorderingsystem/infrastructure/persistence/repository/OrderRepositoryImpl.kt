package com.example.foodorderingsystem.infrastructure.persistence.repository

import com.example.foodorderingsystem.application.mapper.OrderMapper
import com.example.foodorderingsystem.domain.model.order.OrderModel
import com.example.foodorderingsystem.domain.model.order.enums.OrderStatus
import com.example.foodorderingsystem.domain.model.payment.enums.PaymentStatus
import com.example.foodorderingsystem.domain.repository.CustomerRepository
import com.example.foodorderingsystem.domain.repository.ItemRepository
import com.example.foodorderingsystem.domain.repository.OrderRepository
import com.example.foodorderingsystem.domain.repository.PaymentTypeRepository
import com.example.foodorderingsystem.domain.repository.RestaurantRepository
import com.example.foodorderingsystem.infrastructure.persistence.entity.CustomerEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.ItemEntity
import com.example.foodorderingsystem.interfaces.exception.BadRequestException
import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderItemEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.PaymentTypeEntity
import com.example.foodorderingsystem.infrastructure.persistence.entity.RestaurantEntity
import com.example.foodorderingsystem.interfaces.exception.NotFoundException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Service
class OrderRepositoryImpl(
    private val springDataOrderRepository: SpringDataOrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val paymentTypeRepository: PaymentTypeRepository,
    private val itemRepository: ItemRepository,
    private val orderMapper: OrderMapper,
    private val rabbitTemplate: RabbitTemplate,

) : OrderRepository {
    override fun createOrder(orderModel: OrderModel): OrderModel {
        val customer = findCustomer(orderModel)
        val restaurant = findRestaurant(orderModel)
        val paymentType = findPaymentType(orderModel)

        val orderEntity = createOrderEntity(customer, restaurant, paymentType)
        val orderItems = createOrderItems(orderModel, orderEntity)

        orderEntity.items.addAll(orderItems)

        val savedEntity = springDataOrderRepository.save(orderEntity)

        publishOrderCreatedEvent(savedEntity.guid)

        return orderMapper.entityToModel(savedEntity)
    }

    override fun confirmOrder(orderModel: OrderModel) {
        orderModel.confirm()

        val orderEntity = springDataOrderRepository.findById(orderModel.guid
            ?: throw BadRequestException("O pedido não foi encontrado"))
            .orElseThrow { BadRequestException("Pedido não encontrado no banco") }

        orderEntity.status = orderModel.status ?: throw BadRequestException("O status do pedido não foi encontrado")

        val savedEntity = springDataOrderRepository.save(orderEntity)

        publishOrderConfirmedEvent(savedEntity.guid)
    }


    private fun publishOrderCreatedEvent(orderGuid: UUID?) {
        val guid = orderGuid ?: throw BadRequestException("GUID do pedido é nulo")
        rabbitTemplate.convertAndSend("orders.exchange", "orders.created", guid)
    }

    private fun publishOrderConfirmedEvent(orderGuid: UUID?) {
        val guid = orderGuid ?: throw BadRequestException("GUID do pedido é nulo")
        rabbitTemplate.convertAndSend("orders.exchange", "orders.confirmed", guid)
    }

    override fun findById(guid: UUID): OrderModel? {
        val entity = springDataOrderRepository.findWithItemsByGuid(guid)
        return entity?.let { orderMapper.entityToModel(it) }
    }

    override fun save(orderModel: OrderModel): OrderModel {
        val customer = findEntityById(customerRepository, orderModel.customer.guid!!, "Cliente não encontrado")
        val restaurant = findEntityById(restaurantRepository, orderModel.restaurant.guid!!, "Restaurante não encontrado")
        val paymentType = findEntityById(paymentTypeRepository, orderModel.paymentType.guid, "Tipo de pagamento não encontrado")

        val orderEntity = orderMapper.modelToEntity(orderModel)

        orderEntity.customer = customer
        orderEntity.restaurant = restaurant
        orderEntity.paymentType = paymentType

        val orderItems = orderModel.items.mapIndexed { index, itemModel ->
            val itemEntity = findEntityById(itemRepository, itemModel.itemGuid, "Item do pedido na posição $index não encontrado")

            OrderItemEntity(
                item = itemEntity,
                price = itemModel.price ?: BigDecimal.ZERO,
                quantity = itemModel.quantity,
                order = orderEntity
            )
        }

        orderEntity.items.addAll(orderItems)

        val savedEntity = springDataOrderRepository.save(orderEntity)
        return orderMapper.entityToModel(savedEntity)
    }

    private fun findCustomer(orderModel: OrderModel) =
        findEntityById(customerRepository, orderModel.customer.guid!!, "Cliente não encontrado")

    private fun findRestaurant(orderModel: OrderModel) =
        findEntityById(restaurantRepository, orderModel.restaurant.guid!!, "Restaurante não encontrado")

    private fun findPaymentType(orderModel: OrderModel) =
        findEntityById(paymentTypeRepository, orderModel.paymentType.guid, "Tipo de pagamento não encontrado")

    private fun createOrderEntity(
        customer: CustomerEntity,
        restaurant: RestaurantEntity,
        paymentType: PaymentTypeEntity
    ) = OrderEntity(null, customer, restaurant, paymentType)

    private fun createOrderItems(
        orderModel: OrderModel,
        orderEntity: OrderEntity
    ): List<OrderItemEntity> {
        return orderModel.items.map { itemModel ->
            val itemEntity = findEntityById(itemRepository, itemModel.itemGuid, "Item não encontrado")
            val quantity = itemModel.quantity.takeIf { it > 0 } ?: 1
            val price = itemEntity.price.multiply(BigDecimal(quantity))

            OrderItemEntity(
                guid = itemModel.guid,
                order = orderEntity,
                item = itemEntity,
                quantity = quantity,
                price = price
            )
        }
    }

    private fun <T, ID : Any> findEntityById(
        repository: JpaRepository<T, ID>,
        id: ID,
        notFoundMessage: String
    ): T = repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
}