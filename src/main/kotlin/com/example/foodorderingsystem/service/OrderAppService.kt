package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.*
import com.example.foodorderingsystem.repository.*
import com.example.foodorderingsystem.entity.*
import com.example.foodorderingsystem.exception.NotFoundException
import com.example.foodorderingsystem.mapper.OrderMapper
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
        val customerGuid = request.customerGuid
            ?: throw IllegalArgumentException("GUID do cliente não pode ser nulo")

        val restaurantGuid = request.restaurantGuid
            ?: throw IllegalArgumentException("GUID do cliente não pode ser nulo")

        val paymentTypeGuid = request.paymentTypeGuid
            ?: throw IllegalArgumentException("Tipo do pagamento não pode ser nulo")

        val customer = customerRepository.findById(customerGuid)
            .orElseThrow { NotFoundException("Cliente não encontrado") }

        val restaurant = restaurantRepository.findById(restaurantGuid)
            .orElseThrow { NotFoundException("Restaurante não encontrado") }

        val payment = paymentTypeRepository.findById(paymentTypeGuid)
            .orElseThrow { NotFoundException("Tipo de pagamento não encontrado") }

        val order: Order = orderMapper.toEntity(
            request = request,
            customer = customer,
            restaurant = restaurant,
            paymentType = payment
        ) { itemGuid ->
            itemRepository.findById(itemGuid)
                .orElseThrow { NotFoundException("Item com ID $itemGuid não encontrado") }
        }

        val savedOrder = orderServiceImpl.createOrder(order);

        return orderMapper.toDTO(savedOrder);
    }

    fun getOrders(): List<OrderDTO> {
        val orders = orderRepository.findAll()

        return orderMapper.toListDTO(orders)
    }
}