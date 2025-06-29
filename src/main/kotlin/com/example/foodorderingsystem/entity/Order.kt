package com.example.foodorderingsystem.entity

import com.example.foodorderingsystem.enums.OrderStatus
import com.example.foodorderingsystem.enums.PaymentStatus
import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "customer_guid")
    val customer: Customer,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "restaurant_guid")
    val restaurant: Restaurant,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "payment_type_guid")
    val paymentType: PaymentType,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus = PaymentStatus.AWAITING_PAYMENT,

    var createdAt: Date? = Date(),

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<OrderItem> = mutableListOf()
)