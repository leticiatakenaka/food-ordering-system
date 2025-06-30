package com.example.foodorderingsystem.entity

import com.example.foodorderingsystem.enums.OrderStatus
import com.example.foodorderingsystem.enums.PaymentStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "payment_status_logs")
data class PaymentStatusLogs (
    @Id
    @GeneratedValue
    val guid: UUID,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "order_guid")
    val order: Order,

    @Column(name = "old_payment_status")
    @Enumerated(EnumType.STRING)
    var oldPaymentStatus: PaymentStatus,

    @Column(name = "new_payment_status")
    @Enumerated(EnumType.STRING)
    var newPaymentStatus: PaymentStatus,

    @Column(name = "changed_at")
    var changedAt: Date? = null,
)