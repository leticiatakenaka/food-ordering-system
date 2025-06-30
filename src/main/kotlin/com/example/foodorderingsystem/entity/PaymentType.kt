package com.example.foodorderingsystem.entity

import com.example.foodorderingsystem.enums.PaymentType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "payment_types")
data class PaymentType(
    @Id
    @GeneratedValue
    val guid: UUID,

    @Enumerated(EnumType.STRING)
    val description: PaymentType? = null
)
