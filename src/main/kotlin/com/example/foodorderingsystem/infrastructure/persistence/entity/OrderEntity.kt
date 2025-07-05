package com.example.foodorderingsystem.infrastructure.persistence.entity

import com.example.foodorderingsystem.domain.model.order.enums.OrderStatus
import com.example.foodorderingsystem.domain.model.payment.enums.PaymentStatus
import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "customer_guid")
    var customer: CustomerEntity? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "restaurant_guid")
    var restaurant: RestaurantEntity? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "payment_type_guid")
    var paymentType: PaymentTypeEntity? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<OrderItemEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus = PaymentStatus.AWAITING_PAYMENT,

    var createdAt: Date? = Date()
)