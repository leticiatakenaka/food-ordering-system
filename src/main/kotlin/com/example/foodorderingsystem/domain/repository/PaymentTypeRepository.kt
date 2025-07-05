package com.example.foodorderingsystem.domain.repository

import com.example.foodorderingsystem.infrastructure.persistence.entity.PaymentTypeEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PaymentTypeRepository : JpaRepository<PaymentTypeEntity, UUID>{
}