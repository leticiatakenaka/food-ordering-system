package com.example.foodorderingsystem.infrastructure.persistence.entity

import com.example.foodorderingsystem.domain.model.payment.enums.PaymentType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "payment_types")
data class PaymentTypeEntity(
    @Id
    @GeneratedValue
    val guid: UUID,

    @Enumerated(EnumType.STRING)
    val description: PaymentType? = null
)
