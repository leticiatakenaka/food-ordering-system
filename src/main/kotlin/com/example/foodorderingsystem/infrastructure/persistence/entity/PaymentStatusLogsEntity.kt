package com.example.foodorderingsystem.infrastructure.persistence.entity

import com.example.foodorderingsystem.domain.model.payment.enums.PaymentStatus
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
data class PaymentStatusLogsEntity (
    @Id
    @GeneratedValue
    val guid: UUID,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "order_guid")
    val order: OrderEntity,

    @Column(name = "old_payment_status")
    @Enumerated(EnumType.STRING)
    var oldPaymentStatus: PaymentStatus,

    @Column(name = "new_payment_status")
    @Enumerated(EnumType.STRING)
    var newPaymentStatus: PaymentStatus,

    @Column(name = "changed_at")
    var changedAt: Date? = null,
)