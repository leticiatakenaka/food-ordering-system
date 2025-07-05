package com.example.foodorderingsystem.application.service

import com.example.foodorderingsystem.application.dto.order.CreateOrderRequest
import com.example.foodorderingsystem.application.dto.order.OrderDTO
import com.example.foodorderingsystem.domain.repository.OrderRepository
import com.example.foodorderingsystem.application.mapper.OrderMapper
import com.example.foodorderingsystem.infrastructure.persistence.repository.SpringDataOrderRepository
import com.example.foodorderingsystem.interfaces.exception.BadRequestException
import org.springframework.stereotype.*
import java.util.UUID

@Service
class OrderAppService(
    private val orderRepository: OrderRepository,
    private val springDataOrderRepository: SpringDataOrderRepository,
    private val orderMapper: OrderMapper
) {

    fun confirmOrder(orderId: UUID) {
        val order = orderRepository.findById(orderId)

        if (order == null) { throw BadRequestException ("Não existe pedido com esse GUID") }

        orderRepository.confirmOrder(order)
    }

    fun createOrder(createOrderRequest: CreateOrderRequest): OrderDTO {
        val orderModel = orderMapper.dtoToModel(createOrderRequest)

        val savedOrder = orderRepository.createOrder(orderModel)

        return orderMapper.modelToDTO(savedOrder)
    }

    fun getOrders(): List<OrderDTO> {
        val orders = springDataOrderRepository.findAll()

        return orderMapper.toListDTO(orders)
    }
}