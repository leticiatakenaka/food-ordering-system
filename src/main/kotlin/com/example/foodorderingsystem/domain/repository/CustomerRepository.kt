package com.example.foodorderingsystem.domain.repository

import com.example.foodorderingsystem.infrastructure.persistence.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CustomerRepository : JpaRepository<CustomerEntity, UUID> {
}