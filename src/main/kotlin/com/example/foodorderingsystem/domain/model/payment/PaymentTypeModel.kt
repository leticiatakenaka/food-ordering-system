package com.example.foodorderingsystem.domain.model.payment

import java.util.UUID
import com.example.foodorderingsystem.domain.model.payment.enums.PaymentType

class PaymentTypeModel(
    val guid: UUID,
    val description: PaymentType? = null
)