package com.example.foodorderingsystem.repository

import com.example.foodorderingsystem.entity.PaymentType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PaymentTypeRepository : JpaRepository<PaymentType, UUID>{
}