package com.example.foodorderingsystem.repository

import com.example.foodorderingsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CustomerRepository : JpaRepository<Customer, UUID> {
}