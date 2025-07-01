package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.*
import com.example.foodorderingsystem.repository.*
import com.example.foodorderingsystem.entity.*
import com.example.foodorderingsystem.exception.NotFoundException
import com.example.foodorderingsystem.mapper.OrderMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.*

@Service
class OrderAppService(
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val paymentTypeRepository: PaymentTypeRepository,
    private val itemRepository: ItemRepository,
    private val orderRepository: OrderRepository,
    private val orderServiceImpl: OrderServiceImpl,
    private val orderMapper: OrderMapper
) {

    fun createOrder(request: CreateOrderRequest): OrderDTO {
        val customerGuid = requireNotNull(request.customerGuid) { "GUID do cliente não pode ser nulo" }
        val restaurantGuid = requireNotNull(request.restaurantGuid) { "GUID do restaurante não pode ser nulo" }
        val paymentTypeGuid = requireNotNull(request.paymentTypeGuid) { "Tipo do pagamento não pode ser nulo" }

        val customer = findEntityById(customerRepository, customerGuid, "Cliente não encontrado")
        val restaurant = findEntityById(restaurantRepository, restaurantGuid, "Restaurante não encontrado")
        val paymentType = findEntityById(paymentTypeRepository, paymentTypeGuid, "Tipo de pagamento não encontrado")

        val order = orderMapper.toEntity(
            request = request,
            customer = customer,
            restaurant = restaurant,
            paymentType = paymentType
        ) { itemGuid ->
            findEntityById(itemRepository, itemGuid, "Item com ID $itemGuid não encontrado")
        }

        val savedOrder = orderServiceImpl.createOrder(order)
        return orderMapper.toDTO(savedOrder)
    }

    private fun <T, ID : Any> findEntityById(
        repository: JpaRepository<T, ID>,
        id: ID,
        notFoundMessage: String
    ): T = repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }

    fun getOrders(): List<OrderDTO> {
        val orders = orderRepository.findAll()

        return orderMapper.toListDTO(orders)
    }
}